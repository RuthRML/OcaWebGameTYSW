<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.json.JSONObject"%>
<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>

<%

	String p = request.getParameter("p");
	JSONObject jso = new JSONObject(p);	
	String nombre = jso.optString("nombre");
	
	try {
		Manager.get().cerrarSesion(nombre);
	} catch(Exception e){
		System.out.println(e.getMessage());
	}
		
%>