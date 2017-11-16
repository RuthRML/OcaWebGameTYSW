package edu.uclm.esi.tysweb.laoca.dominio;

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

	

}
