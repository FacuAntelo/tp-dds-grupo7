package Organizacion;

import java.util.Date;
import java.util.HashMap;

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
        notificacionesEmail.put(fecha,notificacion);
    }
}
