<%@page import="org.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="org.json.JSONObject"%>
    <%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>

<%
	
	JSONObject respuesta = new JSONObject();

	try{
		JSONArray listaRanking = Manager.get().calcularRanking();
		respuesta.put("listaJugadores", listaRanking);
		respuesta.put("resultado", "OK");
	}catch(Exception e){
		System.out.println("Error al calcular ránking: " + e.getMessage());
		respuesta.put("mensaje", "Error al calcular ránking.");
		respuesta.put("resultado", "ERROR");
	}

	out.print(respuesta.toString());
%>
