<%@page import="edu.uclm.esi.tysweb.laoca.dao.DAOUsuario"%>
<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Usuario"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>
<%@page import="edu.uclm.esi.tysweb.laoca.util.ValidatorUtil"%>
<%
	String p = request.getParameter("p");
	JSONObject jso = new JSONObject(p);
	JSONObject respuesta = new JSONObject();

	try{

        String tipo = jso.optString("tipo");
        if(tipo.equals("USERANDPWD") || tipo.equals("REDSOCIAL")){
        	String email = jso.optString("email");
            String pwd1 = jso.optString("pwd1");
            Usuario usuario = Manager.get().login(email, pwd1);
            session.setAttribute("usuario", usuario);
            respuesta.put("nombreUsuario", usuario.getNombre());
            respuesta.put("resultado","OK");
            respuesta.put("correo", usuario.getCorreo());
            respuesta.put("sesion", session.getId());
            Cookie cookiePass;
            
            if(!pwd1.contains("cipherPass=?")){
            	cookiePass = new Cookie("cookiePass", "cipherPass=?" + DAOUsuario.encriptar(pwd1).asString().getValue());
            	response.addCookie(cookiePass);
            }

        }else{
        	System.out.println("Request login errónea.");
        	respuesta.put("resultado", "ERROR");
        	respuesta.put("mensaje", "Request login errónea.");
        }

    }catch(Exception e){
		System.out.println("Error en el login: " + e.getMessage());
		respuesta.put("resultado", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}
	
	out.println(respuesta.toString());
	
%>