package edu.uclm.esi.tysweb.laoca.dominio;

import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class Partida {

	private ConcurrentHashMap<String, Usuario> jugadores;
	private int numeroDeJugadores;
	private int id;

	public Partida(Usuario creador, int numeroDeJugadores) {
		this.jugadores = new ConcurrentHashMap<>();
		this.jugadores.put(creador.getLogin(), creador);
		this.numeroDeJugadores = numeroDeJugadores;
		this.id = new Random().nextInt();
	}

	public Integer getId() {
		return this.id;
	}

	public void add(Usuario usuario) {
		this.jugadores.put(usuario.getLogin(), usuario);
	}

	public boolean isReady() {
		return this.jugadores.size() == this.numeroDeJugadores;
	}

	public void actualizar(String jugador, int dado) {
		// TODO Auto-generated method stub		
	}

	public void comenzar() {
		JSONObject jso = new JSONObject();
		jso.put("tipo", "COMIENZO");
		Enumeration< Usuario> eJugadores = this.jugadores.elements();
		while(eJugadores.hasMoreElements()) {
			Usuario jugador=eJugadores.nextElement();
			
			//if(cont++==posTurno)
			//	jso.put("jugadorConelturno", jugador.get)
			
			try {//por si se desconecta
				jugador.enviar(jso);
			}catch (Exception e) {
				// TODO: Eliminar de la coleccion, mirar si la partida ha terminado 
				// y decirle al WSServer que quite a este jugador
			}
		}
		
	}
}
