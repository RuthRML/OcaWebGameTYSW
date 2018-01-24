package edu.uclm.esi.tysweb.laoca.dominio;

import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import edu.uclm.esi.tysweb.laoca.util.ValidatorUtil;

public class RecuperarPwd {

    private String email;
    private Exception exception;
    private String codigo;

    public void generarCodigo() {
    	long codigo=new Random().nextLong();
    	this.codigo=""+codigo;
    }
    
    public String getCodigo() {
    	return this.codigo;
    }
    
    public String execute() throws Exception {
    	comprobarCredenciales(email);
        EMailSenderService server=new EMailSenderService();            
        server.enviarPorGmail(this.email, this.codigo);
        return "OK";
    }
    
    private void comprobarCredenciales(String email) throws Exception{
        
        if (email == null || email.length() == 0) {
             throw new Exception("El email no puede estar vacío");
        } else if (email != null && email.length() > 45) {
             throw new Exception("El email no puede tener más de 45 caracteres");
        } else if (!ValidatorUtil.validateEmail(email)) {
             throw new Exception("La dirección de email es inválida");
        }        
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResultado() {
        if (exception!=null)
            return exception.toString();
        return "OK";
    }
}


class EMailSenderService {

    private final Properties properties = new Properties();
    private Session session;
    private String smtpHost, startTTLS, port;
    private String remitente, serverUser, userAutentication, pwd;

    public void enviarPorGmail(String destinatario,String codigo) throws MessagingException {
        this.smtpHost="smtp.gmail.com";
        this.startTTLS="true";
        this.port="465";
        this.remitente="ocawebgame.tysw@gmail.com";
        this.serverUser="ocawebgame.tysw@gmail.com";
        this.userAutentication="true";
        this.pwd="ochoasteriscos";
        properties.put("mail.smtp.host", this.smtpHost);
        properties.put("mail.smtp.starttls.enable",this.startTTLS);
        properties.put("mail.smtp.port", this.port);
        properties.put("mail.smtp.mail.sender",this.remitente);
        properties.put("mail.smtp.user", this.serverUser);
        properties.put("mail.smtp.auth",this.userAutentication);
        properties.put("mail.smtp.socketFactory.port",this.port);
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback","false");
        
        SecurityManager security = System.getSecurityManager();
        
        Authenticator auth = new autentificadorSMTP();
        Session session = Session.getInstance(properties, auth);

        MimeMessage msg = new MimeMessage(session);
        msg.setSubject("La Oca - Recuperación de contraseña");
        msg.setText("Pulsa en el siguiente enlace para crear una nueva contraseña: http://localhost:8080/OcaWebGameTYSW/cambiopwd.html?usuario="+destinatario+"&codigo=" + codigo);
        msg.setFrom(new InternetAddress(this.remitente));
        msg.addRecipient(Message.RecipientType.TO , new InternetAddress(destinatario));
        Transport.send(msg);
    }

    private class autentificadorSMTP extends
        javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(remitente, pwd);
        }
    }
}

