<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>
<%@page import="edu.uclm.esi.tysweb.laoca.util.ValidatorUtil"%>
<%
	String p = request.getParameter("p");
	JSONObject jso = new JSONObject(p);
	JSONObject respuesta = new JSONObject(p);

	try{

        String tipo = jso.optString("tipo");
        String email = jso.optString("email");
        String pwd1 = jso.optString("pwd1");
        String pwd2 = jso.optString("pwd2");
        String boton = request.getParameter("boton");
        if(tipo.equals("USERANDPWD")){
            //comprobarCredenciales( email,  pwd1,  pwd2);
        }   
   
        Manager.get().registrar(email, pwd1);
        respuesta.put("resultado","OK");

    }catch(Exception e){
		System.out.println("El mensaje error es: " + e.getMessage());
		respuesta.put("resultado", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}

	out.println(respuesta.toString());
	
%>