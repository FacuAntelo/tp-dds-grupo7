package Main;

import CargaExcel.ExcelUtils;
import MediosDeTransporte.*;
import Organizacion.Organizacion;
import Sector.Sector;
import Usuarios.Administrador;
import ValidacionExterna.APIInterna;
import Validador.CriterioValidador;
import Validador.ValidadorDePassword;
import Miembro.*;
import trayecto.*;

import java.io.IOException;

import static MediosDeTransporte.Combustible.ELECTRICO;
import static MediosDeTransporte.TipoVehiculo.AUTO;


public class Main {
    public static void main(String[] args) throws IOException {

        /*Prueba del validador*/
        ValidadorDePassword vali = new ValidadorDePassword();
        CriterioValidador validacionPorLongitud = new Validador.ValidacionPorLongitud();
        vali.agregarCriterio(validacionPorLongitud);


        Administrador usuario = new Administrador("nacho555555", "123asdsadasdsad");
        //System.out.print(usuario.validarClave(vali));

        /*Prueba de miembro*/

        //Instancias de miembro
        Organizacion org = new Organizacion();
        Sector sec = new Sector("market", org);
        org.agregarSector(sec);

        //System.out.print(per.getNombre());

        //Base para comparar ficticia
        APIInterna api = new APIInterna();


//        org.darDeAltaMiembro(per, api, sec);

       /* sec.getMiembros().forEach(un_miembro -> {System.out.print(un_miembro.getPersona().getNombre());} );*/

        /*Prueba de trayecto*/

        Localidad localidadOrigen = new Localidad(3);
        Calle calleOrigen = new Calle("suripacha");
        Provincia provinciaOrigen = new Provincia("buenos aires");

        Direccion direccionOrigen = new Direccion(calleOrigen,550,localidadOrigen,provinciaOrigen);

        Localidad localidadIntermedia= new Localidad(7);
        Calle calleIntermedia = new Calle("alpachino");
        Provincia provinciaIntermedia = new Provincia("buenos aires");

        Direccion direccionIntermedia = new Direccion(calleIntermedia,500,localidadIntermedia,provinciaIntermedia);

        Localidad localidadDestino= new Localidad(5);
        Calle calleDestino = new Calle("surCorea");
        Provincia provinciaDestino = new Provincia("buenos aires");

        Direccion direccionDestino = new Direccion(calleOrigen,480,localidadOrigen,provinciaOrigen);

        Trayecto trayecto = new Trayecto();

        Tramo tramo1 = new Tramo();
        Tramo tramo2 = new Tramo();

        MediosDeTransporte tesla = new VehiculoParticular(AUTO,ELECTRICO);

        tramo1.setUbicacionInicio(direccionOrigen);
        tramo1.setUbicacionFinal(direccionIntermedia);
        tramo1.setMedioDeTransporte(tesla);

        tramo2.setUbicacionInicio(direccionIntermedia);
        tramo2.setUbicacionFinal(direccionDestino);
        tramo2.setMedioDeTransporte(tesla);

        trayecto.setPuntoInicio(direccionOrigen);
        trayecto.setPuntoIpuntoFin(direccionDestino);

        trayecto.agregarTramo(tramo1);
        trayecto.agregarTramo(tramo2);

        Miembro miembro = new Miembro("emily", "higa", TipoDocumento.DNI, "05072022" );

        miembro.agregarTrayecto(trayecto);

        miembro.getTrayectos().forEach(trayect -> trayect.calcularDistanciaTrayecto());

        System.out.print(trayecto.getDistanciaTotal());

        /*Prueba excel*/
        /*String path = new String("C:\\Users\\NACHO\\Desktop\\TP DDS\\2022-mi-no-mino-grupo-07\\src\\main\\java\\Main\\Libro1.xlsx");
        ExcelUtils.leerExcel(path);*/
    }

}
