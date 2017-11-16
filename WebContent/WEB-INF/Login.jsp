<%@page import="org.json.JSONObject"%>
<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String p=request.getParameter("p");

	JSONObject jso= new JSONObject(p);
	JSONObject respuesta= new JSONObject(p);
	try{
	String email=jso.optString("email");
	String pwd=jso.optString("pwd");
	validar( email,  pwd) ;
	Manager.get().ingresar(email, pwd);
	    respuesta.put("resultado","OK");
	}
	
	catch(Exception e){
		System.out.println("El mensaje error es: "+e.getMessage());
		respuesta.put("resultado","ERROR");
		respuesta.put("mensaje",e.getMessage());
	}
	out.println(respuesta.toString());
	

%>

<%!
private void validar(String email, String pwd1) throws Exception{
	
        if (pwd1==null || pwd1.length()==0 ) {
            throw new Exception("Digite contraseña");
        }else if (email==null || email.length()==0) {
        	 throw new Exception("El email no puede estar vacío");
        }  /*else if (!ValidatorUtil.validateEmail(email)) {
        	 throw new Exception("La dirección de email es inválida");
        }*/
        
}
%>