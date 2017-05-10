/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;

/**
 *
 * @author Ger
 */
public class Mail {
    
    public boolean mandaMail(String Titulo, String Msj)throws Exception
    {
        boolean envio = false;
        String titulo = Titulo;
        String cuerpo = Msj;
        String de = "marsoftteam15@gmail.com";
        
        // Configuracion de la cuenta de envio de mail
        Properties confMail = new Properties();
        confMail.setProperty("mail.smtp.host", "smtp.gmail.com");
        confMail.setProperty("mail.smtp.starttls.enable", "true");
        //confMail.setProperty("mail.smtps.starttls.enable","true");
        confMail.setProperty("mail.smtp.port", "587");
        confMail.setProperty("mail.smtp.user", de);
        confMail.setProperty("mail.smtp.auth", "true");
        //confMail.setProperty("mail.smtp.ssl.enable","true");
        // Sesion
        Session session = Session.getDefaultInstance(confMail);
        // Creamos el Mail
        MimeMessage correo = new MimeMessage(session);
        correo.setFrom(new InternetAddress(de));
        correo.addRecipient(Message.RecipientType.TO, new InternetAddress(de));
        correo.setSubject(titulo);
        correo.setText(cuerpo);

        // Enviamos MAil .
        Transport t = session.getTransport("smtp");
        t.connect("marsoftteam15@gmail.com", "tugfamarsopa");
        t.sendMessage(correo, correo.getAllRecipients());

        // Cerramos conexion.
        t.close();
        envio = true;
        return envio;
    }
    
}