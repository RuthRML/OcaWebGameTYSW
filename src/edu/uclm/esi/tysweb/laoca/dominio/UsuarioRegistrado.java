package edu.uclm.esi.tysweb.laoca.dominio;

import edu.uclm.esi.tysweb.laoca.dao.DAOUsuario;

public class UsuarioRegistrado extends Usuario {

	public UsuarioRegistrado(String nombreJugador) throws Exception {
		super(nombreJugador);
		// TODO Auto-generated constructor stub
	}

	public UsuarioRegistrado() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Usuario login(String nombreUsuario, String pwd1) throws Exception {
		return DAOUsuario.login(nombreUsuario, pwd1);
	}

}
