<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>
<%@page import="edu.uclm.esi.tysweb.laoca.util.ValidatorUtil"%>
<%
	String nombreUsuario=request.getParameter("nombreUsuario");
    String email=request.getParameter("email");
    String pwd1=request.getParameter("pwd1");
    String pwd2=request.getParameter("pwd2");
    String boton=request.getParameter("boton");
    String msg=null;
    if (boton!=null) {
        if (pwd1==null || pwd2==null || pwd1.length()==0 || pwd2.length()==0) {
            msg="Las contraseñas deben coincidir";
        } else if (!pwd1.equals(pwd2)) {
            msg="Las contraseñas deben coincidir";
        } else if (email==null || email.length()==0) {
            msg="El email no puede estar vacío";
        } else if (email!=null && email.length()>45) {
            msg="El email no puede tener más de 45 caracteres";
        } else if (!ValidatorUtil.validateEmail(email)) {
            msg="La dirección de email es inválida";
        }
        if (msg==null) {
            Manager.get().registrar(nombreUsuario, email, pwd1);
        }
    }
%>