<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="edu.uclm.esi.tysweb.laoca.dominio.*, org.json.*" %>
<%
	JSONObject jso = new JSONObject();
	Usuario usuario = (Usuario) session.getAttribute("usuario");	
	if(usuario==null){
		jso.put("result","ERROR");	
	}else{
		jso.put("nombreUsuario", usuario.getNombre());
		jso.put("result","OK");
	}
	out.print(jso.toString());
%>