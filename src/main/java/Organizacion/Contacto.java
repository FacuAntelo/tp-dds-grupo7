package Organizacion;

import java.util.Date;
import java.util.HashMap;

public interface Contacto {
    //Por el momento tienen email y whatapp tienen el mismo comportamiento cuando llega una notificacion --> Guarda en el hashmap
    public void serNotificado(String linkGuiaDeRecomendaciones, Date fecha);

    public String obtenerContacto();
    public HashMap<Date, String> obtenerNotificaciones();
}
