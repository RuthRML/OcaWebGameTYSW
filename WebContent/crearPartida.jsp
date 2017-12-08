<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>
<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Usuario"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	String p = request.getParameter("p");
	JSONObject jso = new JSONObject(p);
	String nombreJugador = jso.getString("nombre");
	int numeroDeJugadores = jso.getInt("numeroDeJugadores");
	session.setAttribute("nombreDeUsuario", nombreJugador);
	
	JSONObject respuesta = new JSONObject();
	
	try{
		Usuario usuarioPartida = Manager.get().crearPartida(nombreJugador, numeroDeJugadores);

		int idPartida = usuarioPartida.getPartida().getId();
		
		session.setAttribute ("nombreDeUsuario", nombreJugador);
		respuesta.put("result", "OK");
		respuesta.put("mensaje", idPartida);
		
		Cookie cookie = new Cookie ("kookie", "" + numeroDeJugadores);
		cookie.setMaxAge(30); //Expira en 30 segundos
		response.addCookie(cookie);
	}catch (Exception e){
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}
	
	out.println(respuesta.toString());

%>