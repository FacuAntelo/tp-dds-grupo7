package TestOrganizacion;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestNotificacion {
//    Organizacion organizacion = new Organizacion();
//    Notificacion notificacionLink = new Notificacion();
//    Email email = new Email("tp.dds.grupo7.2022@gmail.com");
//    WhatsApp celu = new WhatsApp("+5491127772975");
//    String laNotificacion = "Link notificacioooonnn";
//    String unaFecha = "09-07-2022 02:07";
//
//    @Before
//    public void inicializacion(){
//        organizacion.setRazonSocial("Unilever");
//    }
//
//
//    @Test
//    public void organizacionConContactoSuscribirseANotificaciones(){
//        organizacion.agregarContacto(email);
//        organizacion.agregarContacto(celu);
//        organizacion.activarNotificaciones(notificacionLink);
//        assertTrue(notificacionLink.getOrganizacionesANotificar().contains(organizacion));
//    }
//
//    @Test
//    public void organizacionSinContactoSuscribirseANotificaciones(){
//        organizacion.activarNotificaciones(notificacionLink);
//        assertFalse(notificacionLink.getOrganizacionesANotificar().contains(organizacion));
//    }

//    @Test
//    public void enviarNotificacion() throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
//        Date fecha = sdf.parse(unaFecha);
//
//        organizacion.agregarContacto(email);
//        organizacion.agregarContacto(celu);
//        organizacion.activarNotificaciones(notificacionLink);
//        notificacionLink.enviarNotificacionAOrganizaciones(laNotificacion, fecha);
//        assertTrue(notificacionLink
//                .getOrganizacionesANotificar()
//                .stream().allMatch(o -> o.getContactos()//para todos los contactos de todas las organizaciones suscriptas
//                        .stream().anyMatch(contacto ->contacto.obtenerNotificaciones().get(fecha) ==laNotificacion )));
//    }

}
