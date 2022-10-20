package models.ValidacionExterna;


import models.Miembro.TipoDocumento;

import models.Sector.Sector;

import java.util.ArrayList;
import java.util.List;

public class APIInterna implements ValidadorExterno{
    private List<String> base;
    private List<Sector> sectores;

    public APIInterna(){
        this.base = new ArrayList<>();
        this.base.add("Nacho");
        this.sectores = new ArrayList<Sector>();
//        sectores.add(new Sector("market", new Organizacion()));
    }

    public List<Sector> getSectores() {return sectores;}

    @Override
    public  boolean perteneceMiembro(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento){
        return base.contains(nombre);
    }

    @Override
    public Sector sectorAlQuePertenece(String identificador){
        return sectores.get(0);
    }

//    public boolean puedePernecerALaOrganizacion(Persona persona, boolean valorDeRetorno){
//        //TODO
//        // hace alguna validacion
//        return valorDeRetorno;
//    }



}
