package edu.uclm.esi.tysweb.laoca.dominio;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.websockets.WSPartidas;

public class Partida {

	private int numeroDeJugadores;
	private int id;

	private Vector<Usuario> jugadores;
	private Tablero tablero;
	private Usuario ganador;

	private int jugadorConElTurno;

	public Partida(Usuario creador, int numeroDeJugadores) {
		
		this.jugadores = new Vector<>();
		this.jugadores.add(creador);
		this.numeroDeJugadores = numeroDeJugadores;
		this.id = new Random().nextInt();
		this.tablero = new Tablero();

	}

	public Integer getId() {
		return this.id;
	}

	public void add(Usuario jugador) {
		this.jugadores.add(jugador);
	}

	public boolean isReady() {
		return this.jugadores.size() == this.numeroDeJugadores;
	}

	public void comenzar() {
		JSONObject jso = new JSONObject();
		jso.put("tipo", "COMIENZO");
		jso.put("idPartida", this.id);
		JSONArray jsa = new JSONArray();
		this.jugadorConElTurno = (new Random()).nextInt(this.jugadores.size());
		jso.put("jugadorConElTurno", getJugadorConElTurno().getNombre());
		
		for (Usuario jugador : jugadores) {
			jsa.put(jugador.getNombre());
			addJugadorAlTablero(jugador);
		}
		
		jso.put("jugadores", jsa);
		broadcastPrimeraVez(jso);
	}

	public Usuario getJugadorConElTurno() {
		if (this.jugadores.size() == 0)
			return null;
		return this.jugadores.get(this.jugadorConElTurno);
	}

	public JSONObject tirarDado(String nombreJugador, int dado) throws Exception {
		JSONObject result = new JSONObject();
		Usuario jugador = findJugador(nombreJugador);
		if (jugador != getJugadorConElTurno()) {
			result.put("mensaje", "No tienes el turno");
			throw new Exception("No tienes el turno");			
		}
		result.put("tipo", "TIRADA");
		result.put("casillaOrigen", jugador.getCasilla().getPos());
		result.put("dado", dado);
		Casilla destino = this.tablero.tirarDado(jugador, dado);
		result.put("destinoInicial", destino.getPos());
		Casilla siguiente = destino.getSiguiente();
		boolean conservarTurno = false;
		
		if (siguiente != null) {
			conservarTurno = true;
			String mensaje = destino.getMensaje();
			result.put("destinoFinal", siguiente.getPos());
			result.put("mensaje", mensaje);
			this.tablero.moverAJugador(jugador, siguiente);
			if (siguiente.getPos() == 62) { // Llegada
				this.ganador = jugador;
				result.put("ganador", this.ganador.getNombre());
			}
		}
		
		if (destino.getPos() == 57) { // Muerte
			jugador.setPartida(null);
			result.put("mensaje", jugador.getNombre() + " cae en la muerte.");
			this.jugadorConElTurno--;
			if (this.jugadores.size() == 1) {
				this.ganador = this.jugadores.get(0);
				result.put("ganador", this.ganador.getNombre());
			}
		}
		
		if (destino.getPos() == 62) { // Llegada
			this.ganador = jugador;
			result.put("ganador", this.ganador.getNombre());
		}
		
		int turnosSinTirar = destino.getTurnosSinTirar();
		
		if (turnosSinTirar > 0) {
			String sitio = "";
			if(destino.getPos() == 18)
				sitio = "Taberna";
			else if(destino.getPos() == 30)
				sitio = "Pozo";
			else if(destino.getPos() == 51)
				sitio = "Carcel";
			result.put("mensajeAdicional",
					jugador.getNombre() + " est\u00e1 " + turnosSinTirar + " turnos sin tirar porque ha ca\u00eddo en " + sitio);
			jugador.setTurnosSinTirar(destino.getTurnosSinTirar());
		}
		
		result.put("jugadorConElTurno", pasarTurno(conservarTurno));
		return result;
	}

	private String pasarTurno(boolean conservarTurno) {
		if (!conservarTurno) {
			boolean pasado = false;
			do {
				this.jugadorConElTurno = (this.jugadorConElTurno + 1) % this.jugadores.size();
				Usuario jugador = getJugadorConElTurno();
				int turnosSinTirar = jugador.getTurnosSinTirar();
				if (turnosSinTirar > 0) 
					jugador.setTurnosSinTirar(turnosSinTirar - 1);
				else
					pasado = true;
			} while (!pasado);
		}
		
		return getJugadorConElTurno().getNombre();
	}

	private Usuario findJugador(String nombreJugador) {
		for (Usuario jugador : jugadores)
			if (jugador.getNombre().equals(nombreJugador))
				return jugador;
		return null;
	}

	public void addJugadorAlTablero(Usuario jugador) {
		this.tablero.addJugador(jugador);
	}

	void broadcastPrimeraVez(JSONObject jso) {
		ConcurrentHashMap<String, Session> sesionesid = WSPartidas.getSesionesPorId();
		Enumeration<Session> sesiones = sesionesid.elements();
		while (sesiones.hasMoreElements()) {
			Session sesion = sesiones.nextElement();
			try {
				sesion.getBasicRemote().sendText(jso.toString());
			} catch (IOException e) {
				continue;
			}
		}
	}
	
	void broadcast(JSONObject jso) {
		for (int i = jugadores.size() - 1; i >= 0; i--) {
			Usuario jugador = jugadores.get(i);
			try {
				jugador.enviar(jso);
			} catch (Exception e) {
				this.jugadores.remove(jugador);
			}
		}
	}

	public Vector<Usuario> getJugadores() {
		return this.jugadores;
	}

	public Usuario getGanador() {
		return this.ganador;
	}

	public void terminar() throws Exception {
		for (Usuario jugador : this.jugadores) {
			jugador.setPartida(null);
		}
			getGanador().sumarVictoria();
	}

	@Override
	public String toString() {
		String r = "Partida " + id + "\n";
		for (Usuario jugador : jugadores)
			r += "\t" + jugador + "\n";
		r += "\n";
		return r;
	}

	public JSONObject removeJugador(Usuario usuario) {
		
		JSONObject result = new JSONObject();
		result.put("tipo", "EXPULSADO");
		result.put("mensaje", "Se ha ido un jugador");
		result.put("usuarioExpulsado", usuario.getNombre());	
		this.jugadores.remove(usuario);
		this.numeroDeJugadores--;
		if(this.jugadores.size() > 0) {
			result.put("jugadorConElTurno", pasarTurno(false));		
			if(this.numeroDeJugadores == 1 && this.jugadores.size() == 1) {
				this.ganador = this.jugadores.firstElement();
				result.put("ganador", this.ganador.getNombre());
			}		
		}
		return result;
	}
}