package Organizacion;
import Sector.Sector;
import Miembro.*;
import ValidacionExterna.ValidadorExterno;

import java.util.ArrayList;
import java.util.List;

public class Organizacion {
    private String razonSocial;
    private TipoOrganizacion tipoOrganizacion;
    private Ubicacion ubicacion;
    private List<Sector> sectores;
    private String clasificacion;
    private List<Persona> relacionesPendientesAConfirmar; //SE NECESITA UNA LISTA DE RELACIONES PENDIENTES A CONFIRMAR???

    public Organizacion(){
        this.relacionesPendientesAConfirmar = new ArrayList<>();
        this.sectores = new ArrayList<>();
    }

    public void recibePeticion(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento, ValidadorExterno validadorPertenencia, Sector sector){
        if(puedeSerMiembro(nombre, apellido, tipoDocumento, nroDocumento, validadorPertenencia)){
            darDeAltaMiembro(nombre, apellido,tipoDocumento, nroDocumento,validadorPertenencia, sector);
        }else{
            System.out.print("No se puede dar de alta a la parsona");

        }
    }
    //COMO SE ASIGNA EL SECTOR?? VIENE EN LA PETICION O DEVUELVE EL VALIDADOR EXTERNO??
    public void darDeAltaMiembro(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento, ValidadorExterno validadorExterno, Sector sector){
        sector.agregarMiembro(new Miembro(nombre, apellido, tipoDocumento, nroDocumento));
    }

    public boolean puedeSerMiembro(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento, ValidadorExterno validadorPertenencia){
        return validadorPertenencia.perteneceMiembro(nombre, apellido, tipoDocumento, nroDocumento);
    }
//    public void darDeAltaMiembro(Persona persona, ValidadorExterno validadorPertenencia, Sector sector){
//        if(validarPertenenciaDeMiembro(persona, validadorPertenencia)){
//            validadorPertenencia.sectorAlQuePertenece(persona, sector).agregarMiembro(new Miembro(persona));
//
//        }else{
//            System.out.print("No se puede dar de alta a la parsona");
//
//        }
//    }


//SE NECESITA UNA LISTA DE RELACIONES PENDIENTES A CONFIRMAR???
//    public void confirmarRelacionesPendientes(APIInterna validadorPertenencia, Sector sector){
//        this.relacionesPendientesAConfirmar.forEach(solicitud->{
//            this.darDeAltaMiembro(solicitud, validadorPertenencia, sector);
//        });
//    }
    public void agregarMiembroPendiente(Persona persona){this.relacionesPendientesAConfirmar.add(persona);}

    public void agregarSector(Sector sector){
        this.sectores.add(sector);
    }

    public Integer getPosSectorActual(Sector sector){
        return this.sectores.indexOf(sector);
    }



    public Double calcularHCdeLaOrg(){
        return 0.0;
    }





}
