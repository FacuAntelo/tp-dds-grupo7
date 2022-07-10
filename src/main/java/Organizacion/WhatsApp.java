package Organizacion;

import java.util.Date;
import java.util.HashMap;

public class WhatsApp implements Contacto {
    private String nroCelular;
    private HashMap<Date, String> notificacionesWhatsApp;

    public WhatsApp(String unNro) {
        this.nroCelular= unNro;
        this.notificacionesWhatsApp = new HashMap<>();
    }

    public String obtenerContacto() {return nroCelular;}
    public HashMap<Date, String> obtenerNotificaciones() {return notificacionesWhatsApp;}

    public void setNroCelular(String nroCelular) {this.nroCelular = nroCelular;}


    @Override
    public void serNotificado(String notificacion, Date fecha) {
        notificacionesWhatsApp.put(fecha,notificacion);
    }
}
