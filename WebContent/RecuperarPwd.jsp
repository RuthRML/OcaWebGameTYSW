<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Usuario"%>
<%@page import="org.json.JSONObject"%>
<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>
<%@page import="edu.uclm.esi.tysweb.laoca.util.ValidatorUtil"%>

<%
	String p = request.getParameter("p");
	JSONObject jso = new JSONObject(p);
	JSONObject respuesta = new JSONObject();

	try{
        String tipo = jso.optString("tipo");
        String email = jso.optString("email");
       	
        if(tipo.equals("RECUPERARPWD")){
        	
        	try{
            	String resultado = Manager.get().recuperarPwd(email);
            	respuesta.put("nombreUsuario", email);
            	respuesta.put("resultado", resultado);
            
        	}catch(Exception e){
        		respuesta.put("nombreUsuario", email);
                respuesta.put("resultado", e.getMessage());
        	}          
            
        }      
        
    }catch(Exception e){
		System.out.println("El mensaje error es: " + e.getMessage());
		respuesta.put("resultado", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}

	out.print(respuesta.toString());
%>

<%!
private void comprobarCredenciales(String email, String pwd1, String pwd2) throws Exception{
    
        if (pwd1 == null || pwd2 == null || pwd1.length() == 0 || pwd2.length() == 0) {
            throw new Exception("Las contraseñas deben coincidir");
        } else if(pwd1.length() < 4){
            throw new Exception("La contraseña debe ser mínimo de 4 caracteres");
        }else if (!pwd1.equals(pwd2)) {
            throw new Exception("Las contraseñas deben coincidir");
        } else if (email == null || email.length() == 0) {
             throw new Exception("El email no puede estar vacío");
        } else if (email != null && email.length() > 45) {
             throw new Exception("El email no puede tener más de 45 caracteres");
        } else if (!ValidatorUtil.validateEmail(email)) {
             throw new Exception("La dirección de email es inválida");
        }
        
}
%>
