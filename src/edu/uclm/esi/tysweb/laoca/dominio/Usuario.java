package edu.uclm.esi.tysweb.laoca.dominio;

import java.io.IOException;

import javax.websocket.Session;

import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.dao.DAOUsuario;

public class Usuario {
	

	protected String login;
	protected Partida partida;
	private Session sesion;

	public Usuario(String nombreJugador) throws Exception {
		
		if (!DAOUsuario.existe(nombreJugador)) {
			throw new Exception ("Usuario no registrado");
		}		
		this.login = nombreJugador;
	}

	public Usuario() {
		
	}

	public String getLogin() {
		return this.login;
	}

	public void setNombre(String email) {
		this.login=email;
	}

	public  void insert(String pwd1) throws Exception {
		DAOUsuario.insert(this,pwd1);
		
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public void setWSSession(Session sesion) {
		this.sesion=sesion;
	}

	public void enviar(String jugador, int dado) {
		// TODO Auto-generated method stub
		
	}

	public void enviar(JSONObject jso) throws IOException {
		this.sesion.getBasicRemote().sendText(jso.toString());
		
	}	

}
