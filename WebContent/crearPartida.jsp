<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Usuario"%>
<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String p = request.getParameter("p");
	JSONObject jso = new JSONObject(p);

	JSONObject respuesta = new JSONObject();

	try {
		String nombreJugador = jso.getString("nombre");
		int numeroDeJugadores = jso.getInt("numeroDeJugadores");
		Usuario usuarioPartida = Manager.get().crearPartida(nombreJugador, numeroDeJugadores);

		int idPartida = usuarioPartida.getPartida().getId();

		session.setAttribute("usuario", usuarioPartida);
		respuesta.put("result", "OK");
		respuesta.put("mensaje", idPartida);

	} catch (Exception e) {
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}

	out.println(respuesta.toString());
%>