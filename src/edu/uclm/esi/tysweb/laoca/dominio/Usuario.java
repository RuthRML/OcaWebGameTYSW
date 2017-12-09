/*package edu.uclm.esi.tysweb.laoca.dominio;

import java.sql.SQLException;

import org.bson.BsonDocument;

import edu.uclm.esi.tysweb.laoca.dao.DAOUsuario;

public class Usuario {
	

	protected String login;
	protected Partida partida;

	public Usuario(String nombreJugador) throws Exception {
		
		if (!DAOUsuario.existe(nombreJugador)) {
			throw new Exception ("Usuario no registrado");
		}
		
		this.login = nombreJugador;
	}

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public String getLogin() {
		return this.login;
	}

	public static void insert(String nombreUsuario, String email, String pwd) throws SQLException {
		DAOUsuario.registrar(nombreUsuario, email, pwd);
	}

	public void setNonbre(String email) {
		this.login=email;
		
	}

	public  void insert(String pwd1) throws Exception {
		DAOUsuario.insert(this,pwd1);
		
	}

	public boolean entrar(String pwd1)throws Exception  {
		if(DAOUsuario.enter(this,pwd1)) {
			return true;
		}
		return false;
		
	}

	public void setPartida(Partida partida2) {
		// TODO Auto-generated method stub
		
	}

	public Partida getPartida() {
		// TODO Auto-generated method stub
		return null;
	}

	

}*/

package edu.uclm.esi.tysweb.laoca.dominio;

import java.io.IOException;

import javax.websocket.Session;

import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.dao.DAOUsuario;

public class Usuario {
	protected String login;
	protected Partida partida;
	private Session sesion;
	private boolean estaRegistrado = true;
	private int turnosSinTirar;

	private Casilla casilla;

	public Usuario(String nombreJugador) throws Exception {
		if (!DAOUsuario.existe(nombreJugador))
			estaRegistrado = false;
		// throw new Exception("Usuario no registrado");
		this.login = nombreJugador;
	}

	public Usuario() {
	}

	public String getLogin() {
		return this.login;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setWSSession(Session sesion) {
		this.sesion = sesion;
	}

	public void enviar(String jugador, int dado) {
		// TODO Auto-generated method stub

	}

	public void setNombre(String email) {
		this.login = email;
	}

	public void insert(String pwd) throws Exception {
		DAOUsuario.insert(this, pwd);
	}

	public void enviar(JSONObject jso) throws IOException {
		this.sesion.getBasicRemote().sendText(jso.toString());

	}

	public Casilla getCasilla() {
		return this.casilla;
	}

	public void setCasilla(Casilla casilla) {
		this.casilla = casilla;
	}

	public int getTurnosSinTirar() {
		return this.turnosSinTirar;
	}

	public void setTurnosSinTirar(int turnosSinTirar) {
		this.turnosSinTirar = turnosSinTirar;
	}

	@Override
	public String toString() {
		return this.login + " jugando en " + (this.partida != null ? this.partida.getId() : "ninguna ") + ", "
				+ this.casilla.getPos() + ", turnos: " + this.turnosSinTirar;
	}

}
