package Organizacion;

import java.util.Date;
import java.util.HashMap;

public interface Contacto {
    public void serNotificado(String linkGuiaDeRecomendaciones, Date fecha);
    public String obtenerContacto();
    public HashMap<Date, String> obtenerNotificaciones();
}
