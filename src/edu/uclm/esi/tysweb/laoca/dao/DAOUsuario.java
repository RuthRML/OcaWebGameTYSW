package edu.uclm.esi.tysweb.laoca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOUsuario {

	public static boolean existe(String nombreJugador) throws Exception {
		String sql = "SELECT count(*) FROM usuario WHERE email=?";
		Connection bd = DBBroker.get().getBD();
		PreparedStatement ps = bd.prepareStatement(sql);
		ps.setString(1,  nombreJugador);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int resultado = rs.getInt(1);
		DBBroker.get().close(bd);
		return resultado == 1;
	}

}
