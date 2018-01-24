<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>
<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Usuario"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	String p = request.getParameter("p");
	JSONObject jso = new JSONObject(p);
	String nombreJugador = jso.getString("nombre");
	
	JSONObject respuesta = new JSONObject();
	
	try{		
		Usuario usuario = Manager.get().addJugador(nombreJugador);
		session.setAttribute("usuario", usuario);
		int idPartida = usuario.getPartida().getId();
		respuesta.put("mensaje", idPartida);
		respuesta.put("result", "OK");
	}catch (Exception e){
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}
	
	out.println(respuesta.toString());

%>