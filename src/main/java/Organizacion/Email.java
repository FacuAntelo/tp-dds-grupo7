package Organizacion;

import javax.swing.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email implements Contacto {
    private String email;
    private HashMap<Date, String> notificacionesEmail;

    public Email(String unEmail) {
        this.email= unEmail;
        this.notificacionesEmail = new HashMap<>();
    }
    public String obtenerContacto() {return email;}
    public HashMap<Date, String> obtenerNotificaciones() {return notificacionesEmail;}
    public void setEmail(String email) {this.email = email;}


    @Override
    public void serNotificado(String notificacion, Date fecha) {
        try {
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "smtp.office365.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");
            propiedades.setProperty("mail.smtp.auth", "true");

            propiedades.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
            Session sesion = Session.getDefaultInstance(propiedades);
            String correo_emisor = "tp.dds.grupo7.2022@outlook.com";
            String contraseña_emisor = "tpdds72022";

            String correo_receptor = email;
            String asunto = "TP DDS - Grupo 7";
            String mensaje = notificacion;


            MimeMessage message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(correo_emisor));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo_receptor));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correo_emisor,contraseña_emisor);
            transporte.sendMessage(message , message.getRecipients(Message.RecipientType.TO));
            transporte.close();

            System.out.println("Se ha enviado notificacion al Email - " + new Date());

        } catch (AddressException ex) {
            JOptionPane.showMessageDialog(null,"Error : " +ex);
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null,"Error : " +ex);
        }
    }
}
