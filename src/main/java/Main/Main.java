package Main;

import CargaExcel.ExcelUtils;
import MediosDeTransporte.*;
import Notificacion.*;
import Organizacion.*;
import Sector.Sector;
import Usuarios.Administrador;
import ValidacionExterna.APIInterna;
import Validador.*;
import Miembro.*;
import trayecto.*;

import java.io.IOException;
import java.text.ParseException;




public class Main {
    public static void main(String[] args) throws IOException, ParseException {

//        /*Prueba del validador*/
//        ValidadorDePassword vali = new ValidadorDePassword();
//        CriterioValidador validacionPorLongitud = new Validador.ValidacionPorLongitud();
//        vali.agregarCriterio(validacionPorLongitud);
//
//
//        Administrador usuario = new Administrador("nacho555555", "123asdsadasdsad");
//        //System.out.print(usuario.validarClave(vali));
//
//        /*Prueba de miembro*/
//
//        //Instancias de miembro
//        Organizacion org = new Organizacion();
//        Sector sec = new Sector("market", org);
//        org.agregarSector(sec);
//
//        //System.out.print(per.getNombre());
//
//        //Base para comparar ficticia
//        APIInterna api = new APIInterna();
//
//
////        org.darDeAltaMiembro(per, api, sec);
//
//       /* sec.getMiembros().forEach(un_miembro -> {System.out.print(un_miembro.getPersona().getNombre());} );*/
//
//        /*Prueba de trayecto*/
//
//        Localidad localidadOrigen = new Localidad(3);
//        Calle calleOrigen = new Calle("suripacha");
//        Provincia provinciaOrigen = new Provincia("buenos aires");
//
//        Direccion direccionOrigen = new Direccion(calleOrigen,550,localidadOrigen,provinciaOrigen);
//
//        Localidad localidadIntermedia= new Localidad(7);
//        Calle calleIntermedia = new Calle("alpachino");
//        Provincia provinciaIntermedia = new Provincia("buenos aires");
//
//        Direccion direccionIntermedia = new Direccion(calleIntermedia,500,localidadIntermedia,provinciaIntermedia);
//
//        Localidad localidadDestino= new Localidad(5);
//        Calle calleDestino = new Calle("surCorea");
//        Provincia provinciaDestino = new Provincia("buenos aires");
//
//        Direccion direccionDestino = new Direccion(calleOrigen,480,localidadOrigen,provinciaOrigen);
//
//        Trayecto trayecto = new Trayecto();
//
//        Tramo tramo1 = new Tramo();
//        Tramo tramo2 = new Tramo();
//
//        MediosDeTransporte tesla = new VehiculoParticular(AUTO,ELECTRICO);
//
//        tramo1.setUbicacionInicio(direccionOrigen);
//        tramo1.setUbicacionFinal(direccionIntermedia);
//        tramo1.setMedioDeTransporte(tesla);
//
//        tramo2.setUbicacionInicio(direccionIntermedia);
//        tramo2.setUbicacionFinal(direccionDestino);
//        tramo2.setMedioDeTransporte(tesla);
//
//        trayecto.setPuntoInicio(direccionOrigen);
//        trayecto.setPuntoIpuntoFin(direccionDestino);
//
//        trayecto.agregarTramo(tramo1);
//        trayecto.agregarTramo(tramo2);
//
//        Miembro miembro = new Miembro("emily", "higa", TipoDocumento.DNI, "05072022" );
//
//        miembro.agregarTrayecto(trayecto);
//
//        miembro.getTrayectos().forEach(trayect -> trayect.calcularDistanciaTrayecto());
//
//        System.out.print(trayecto.getDistanciaTotal());
//
//        /*Prueba excel*/
//        /*String path = new String("C:\\Users\\NACHO\\Desktop\\TP DDS\\2022-mi-no-mino-grupo-07\\src\\main\\java\\Main\\Libro1.xlsx");
//        ExcelUtils.leerExcel(path);*/
//
//        Administrador admi = new Administrador("Jose", "sadasda");
//        admi.FactorDeEmision();
//        System.out.println(admi.getFactorDeEmisiones());

        //PRUEBA ENVIO DE NOTIFICACIONES A ORGANIZACIONES
        Organizacion organizacionNotif = new Organizacion();
        Email email = new Email("tp.dds.grupo7.2022@gmail.com");
        WhatsApp celu = new WhatsApp("+5491127772975");
        Notificacion notificacionLink = new Notificacion();
        organizacionNotif.setRazonSocial("Unilever");
        organizacionNotif.agregarContacto(email);
        organizacionNotif.agregarContacto(celu);
        organizacionNotif.getContactos().forEach(contacto -> System.out.println(contacto.obtenerContacto()));
        organizacionNotif.activarNotificaciones(notificacionLink);

        notificacionLink.setearEnvioDeNotificaciones("09-07-2022 23:49", "Link notificacioooonnn"); //FECHA -> "dd-mm-aaaa hh:mm"

    }

}
