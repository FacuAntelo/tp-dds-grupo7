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
    private List<Persona> relacionesPendientesAConfirmar;

    public Organizacion(){
        this.relacionesPendientesAConfirmar = new ArrayList<>();
        this.sectores = new ArrayList<>();
    }

    public void darDeAltaMiembro(Persona persona, ValidadorExterno validadorPertenencia){
        if(validarPertenenciaDeMiembro(persona, validadorPertenencia)){
            validadorPertenencia.sectorAlQuePertenece(persona).agregarMiembro(new Miembro(persona));
            //No deberia pasar el sector, sino la entidad externa tiene esa info.
        }else{
            validadorPertenencia.sectorAlQuePertenece(persona).agregarMiembro(new Miembro(persona));
        }
    }

    public void confirmarRelacionesPendientes( ValidadorExterno validadorPertenencia){
        this.relacionesPendientesAConfirmar.forEach(solicitud->{
            this.darDeAltaMiembro(solicitud, validadorPertenencia);
        });
    }

    public void agregarSector(Sector sector){
        this.sectores.add(sector);
    }

    public Integer getPosSectorActual(Sector sector){
        return this.sectores.indexOf(sector);
    }

    public boolean validarPertenenciaDeMiembro(Persona persona, ValidadorExterno validadorPertenencia){
        return validadorPertenencia.perteneceMiembro(persona);
    }

    public void agregarMiembroPendiente(Persona persona){
        this.relacionesPendientesAConfirmar.add(persona);
    }
}
