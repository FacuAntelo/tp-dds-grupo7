package Main;

import CargaExcel.ExcelUtils;
import Combustible.Combustible;
import MediosDeTransporte.*;
import Notificacion.*;
import Organizacion.*;
import Sector.Sector;
import Usuarios.Administrador;
import Usuarios.FactorDeEmision;
import ValidacionExterna.APIInterna;
import Validador.*;
import Miembro.*;
import domain.services.ServicioGeoDDS;
import domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import trayecto.*;

import java.io.IOException;
import java.text.ParseException;

import static MediosDeTransporte.TipoVehiculo.MOTO;
import static Miembro.TipoDocumento.DNI;
import static Organizacion.TipoOrganizacion.EMPRESA;


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
//        Organizacion organizacionNotif = new Organizacion();
//        Email email = new Email("tp.dds.grupo7.2022@gmail.com");
//        WhatsApp celu = new WhatsApp("+5491127772975");
//        Notificacion notificacionLink = new Notificacion();
//        organizacionNotif.setRazonSocial("Unilever");
//        organizacionNotif.agregarContacto(email);
//        organizacionNotif.agregarContacto(celu);
//        organizacionNotif.getContactos().forEach(contacto -> System.out.println(contacto.obtenerContacto()));
//        organizacionNotif.activarNotificaciones(notificacionLink);
//
//        notificacionLink.setearEnvioDeNotificaciones("09-07-2022 23:49", "Link notificacioooonnn"); //FECHA -> "dd-mm-aaaa hh:mm"

        ServicioGeoDDS servicioGeoDDS = ServicioGeoDDS.getInstance();
        servicioGeoDDS.setAdapter(new ServicioGeoDDSRetrofitAdapter());

        // ORGANIZACION //
        // UBICACION //
        Ubicacion ubicacionCocaCola= new Ubicacion();
            // DIRECCION //
        Localidad ezeiza = new Localidad(180);
        Provincia buenosAires = new Provincia("Buenos Aires");
        Direccion direccionCocaCola = new Direccion("Medrano",1500,ezeiza,buenosAires);
            // FIN DIRECCION
        ubicacionCocaCola.setCodigoPostal(1804);
        ubicacionCocaCola.setDireccion(direccionCocaCola);
        // FIN UBICACION //
        // TIPO DE LA ORGANIZACION //
        TipoOrganizacion tipoEmpresa = EMPRESA;
        // FIN TIPO DE LA ORGANIZACION //
        // CLASIFICACION //
        Clasificacion clasificacionProductor = new Clasificacion();
        clasificacionProductor.setNombre("Productor");
        // FIN CLASIFICACION //
        Organizacion cocaCola = new Organizacion("Los cojarudos",tipoEmpresa,clasificacionProductor, ubicacionCocaCola);
        // FIN ORGANIZACION //
        // SECTOR //
        Sector ventasCocaCola = new Sector("Ventas",cocaCola);
        cocaCola.agregarSector(ventasCocaCola);

        Sector administracionCocaCola = new Sector("Administracion",cocaCola);
        cocaCola.agregarSector(administracionCocaCola);

        Sector marketingCocaCola = new Sector("marketing",cocaCola);
        cocaCola.agregarSector(marketingCocaCola);
        // FIN SECTORES //

        // MIEMBROS //
        Miembro oscarAdministracion = new Miembro("Oscar","Longaniza",DNI,"17545848");
        administracionCocaCola.agregarMiembro(oscarAdministracion);

        Miembro vanesaMarketing = new Miembro("Vanesa", "lartori",DNI,"4564564");
        marketingCocaCola.agregarMiembro(vanesaMarketing);

        Miembro edgardoVentas = new Miembro("Edgardo","Rosales",DNI,"468465");
        ventasCocaCola.agregarMiembro(edgardoVentas);

        Miembro teresaMarketing = new Miembro("Teresa","Menta granizada",DNI,"4565465");
        marketingCocaCola.agregarMiembro(teresaMarketing);

        // FIN MIEMBROS //

        // DIRECCIONES Y TRAYECTOS DE LOS MIEMBROS //
        Direccion direccioCasanOscar = new Direccion("COD Warzone", 2, ezeiza,buenosAires);
        Trayecto trayectoOscar = new Trayecto(direccioCasanOscar,direccionCocaCola);

        Direccion direccionCasaVanesa = new Direccion("LOL",20020,ezeiza,buenosAires);
        Trayecto trayectoVanesa = new Trayecto(direccionCasaVanesa,direccionCocaCola);

        Direccion direccionCasaEdgardo = new Direccion("CS:GO", 205,ezeiza,buenosAires);
        Trayecto trayectoEdgardo = new Trayecto(direccionCasaEdgardo,direccionCocaCola);

        Direccion direccionCasaTeresa = new Direccion("outlast",101,ezeiza,buenosAires);
        Trayecto trayectoTeresa = new Trayecto(direccionCasaTeresa,direccionCocaCola);
        // FIN DIRECCIONES Y TRAYECTOS DE LOS MIEMBROS
        // TRAMOS Y MEDIOS DE TRANSPORTE

        //EL TRAYECTO DE VANESA
        Pie pie = new Pie();
        FactorDeEmision naftaFactorDeEmision = new FactorDeEmision();
        Combustible nafta = new Combustible("nafta");
            //DE SU CASA AL GARAJE
        Direccion direccionGarageVanesa = new Direccion("SUPER GARAJE",8550,ezeiza,buenosAires);
        Tramo tramoVanesaAlGarage = new Tramo(direccionCasaTeresa,direccionGarageVanesa);
        tramoVanesaAlGarage.setMedioDeTransporte(pie);
        trayectoTeresa.agregarTramo(tramoVanesaAlGarage);
            // DEL GARAJE A LA COCA COLA
        // COMBUSTIBLE //

        Tramo tramoVanesaACocaCola = new Tramo(direccionGarageVanesa,direccionCocaCola);
        VehiculoParticular bmwVanesa = new VehiculoParticular(MOTO,nafta,true);



    }

}
