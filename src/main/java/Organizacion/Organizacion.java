package Organizacion;
import Sector.Sector;
import Miembro.*;
import ValidacionExterna.APIInterna;
import ValidacionExterna.ValidadorExterno;
import CargaExcel.ExcelUtils;

import java.nio.file.Path;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class Organizacion {
    private String razonSocial;
    private TipoOrganizacion tipoOrganizacion;
    private Ubicacion ubicacion;
    private List<Sector> sectores;
    private String clasificacion;
    private List<Persona> relacionesPendientesAConfirmar;

    public Organizacion(){
        this.relacionesPendientesAConfirmar = new ArrayList<>();
        this.sectores = new ArrayList<>();
    }

    public void darDeAltaMiembro(Persona persona, APIInterna validadorPertenencia, Sector sector){
        if(validarPertenenciaDeMiembro(persona, validadorPertenencia)){
            validadorPertenencia.sectorAlQuePertenece(persona, sector).agregarMiembro(new Miembro(persona));

        }else{
            System.out.print("No se puede dar de alta a la parsona");

        }
    }

    /*public void darDeAltaMiembro2(Persona persona, APIInterna validadorExterno, Sector sector){
        if(validadorExterno.puedePernecerALaOrganizacion(persona, true)){
            sector.agregarMiembro(new Miembro(persona));

        }else{
            System.out.print("No se puede dar de alta a la parsona");

        }
    }*/

    public void confirmarRelacionesPendientes(APIInterna validadorPertenencia, Sector sector){
        this.relacionesPendientesAConfirmar.forEach(solicitud->{
            this.darDeAltaMiembro(solicitud, validadorPertenencia, sector);
        });
    }

    public void agregarSector(Sector sector){
        this.sectores.add(sector);
    }

    public Integer getPosSectorActual(Sector sector){
        return this.sectores.indexOf(sector);
    }

    public boolean validarPertenenciaDeMiembro(Persona persona, APIInterna validadorPertenencia){
        return validadorPertenencia.perteneceMiembro(persona);
    }

    public void agregarMiembroPendiente(Persona persona){
        this.relacionesPendientesAConfirmar.add(persona);
    }

    public Double calcularHCdeLaOrg(){
        return 0.0;
    }





}
