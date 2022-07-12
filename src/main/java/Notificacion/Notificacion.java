package Notificacion;

import Organizacion.Organizacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Notificacion {
    private List<Organizacion> organizacionesANotificar;

    public Notificacion( ) {
        this.organizacionesANotificar = new ArrayList<Organizacion>();
    }

    public List<Organizacion> getOrganizacionesANotificar() {return organizacionesANotificar;}

    //ENTREGA 3 --> PUNTO 5: NOTIFICACIONES
    public void agregarOrganizacionANotificar( Organizacion organizacion){
        organizacionesANotificar.add(organizacion);
    }

    public void setearEnvioDeNotificaciones(String fechaDeEnvio, String notificacion) throws ParseException {
        Date fecha = convertirFechaStringADate(fechaDeEnvio);
        Timer timer = new Timer();
        TimerTask tareaCalendarizada = new TimerTask() {
            @Override
            public void run() {
                enviarNotificacionAOrganizaciones(notificacion, fecha);
                System.out.println("Se han enviado las notificaciones a las " + new Date());
            }
        };
        timer.schedule(tareaCalendarizada, fecha);

    }

    public void enviarNotificacionAOrganizaciones(String notificacion, Date fecha){
        organizacionesANotificar.forEach(organizacion -> organizacion.serNotificado(notificacion, fecha));
    }
    private Date convertirFechaStringADate(String fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
        return sdf.parse(fecha);
    }

}
