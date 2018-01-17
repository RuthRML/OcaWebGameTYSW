package edu.uclm.esi.tysweb.laoca.dominio;

import edu.uclm.esi.tysweb.laoca.dao.DAOUsuario;

public class UsuarioRegistrado extends Usuario {

	public UsuarioRegistrado(String correoJugador) throws Exception {
		super(correoJugador);
	}

	public UsuarioRegistrado() {
		super();
	}

	public static Usuario login(String correoUsuario, String pwd1) throws Exception {
		return DAOUsuario.login(correoUsuario, pwd1);
	}

}
