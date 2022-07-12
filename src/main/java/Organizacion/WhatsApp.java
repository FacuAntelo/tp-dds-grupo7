package Organizacion;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.util.Date;
import java.util.HashMap;

public class WhatsApp implements Contacto {
    private String nroCelular;
    private HashMap<Date, String> notificacionesWhatsApp;
    public static final String ACCOUNT_SID = "ACc43540f84d970517172a1ba83d3f5295";
    public static final String AUTH_TOKEN = "313922234f47a26848653b44b036b5c7";

    public WhatsApp(String unNro) {
        this.nroCelular= unNro;
        this.notificacionesWhatsApp = new HashMap<>();
    }

    public String obtenerContacto() {return nroCelular;}
    public HashMap<Date, String> obtenerNotificaciones() {return notificacionesWhatsApp;}

    public void setNroCelular(String nroCelular) {this.nroCelular = nroCelular;}


    @Override
    public void serNotificado(String notificacion, Date fecha) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:"+nroCelular),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        notificacion)
                .create();
        System.out.println(message.getSid());
        System.out.println("Se ha enviado notificacion al WhatssApp - " + new Date());


    }
}
