package models.Organizacion;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;


@Entity
@DiscriminatorValue("whatsapp")
public class WhatsApp extends Contacto {
    @Transient
    private HashMap<Date, String> notificacionesWhatsApp;
    @Transient
    public static final String ACCOUNT_SID = "ACc43540f84d970517172a1ba83d3f5295";
    @Transient
    public static final String AUTH_TOKEN = "313922234f47a26848653b44b036b5c7";

    public WhatsApp(String unNro) {
        this.setDetalle(unNro);
        this.notificacionesWhatsApp = new HashMap<>();
    }

    public String obtenerContacto() {return this.getDetalle();}
    public HashMap<Date, String> obtenerNotificaciones() {return notificacionesWhatsApp;}

    public void setNroCelular(String nroCelular) {this.setDetalle(nroCelular);}


    @Override
    public void serNotificado(String notificacion, Date fecha) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:"+this.getDetalle()),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        notificacion)
                .create();
        System.out.println(message.getSid());
        System.out.println("Se ha enviado notificacion al WhatssApp - " + new Date());


    }
}
