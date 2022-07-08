package Organizacion;
import Sector.*;
import Miembro.*;
import ValidacionExterna.*;

import java.util.ArrayList;
import java.util.List;

public class Organizacion {
    private String razonSocial;
    private TipoOrganizacion tipoOrganizacion;
    private Ubicacion ubicacion;
    private List<Sector> sectores;
    private String clasificacion;

    public Organizacion(){
        this.sectores = new ArrayList<>();
    }

    public void recibePeticion(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento, ValidadorExterno validadorPertenencia){
        if(puedeSerMiembro(nombre, apellido, tipoDocumento, nroDocumento, validadorPertenencia)){
            darDeAltaMiembro(nombre, apellido,tipoDocumento, nroDocumento,validadorPertenencia);
        }else{
            System.out.print("No se puede dar de alta a la parsona");

        }
    }

    public void darDeAltaMiembro(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento, ValidadorExterno validadorExterno){
        validadorExterno.sectorAlQuePertenece(nroDocumento).agregarMiembro(new Miembro(nombre, apellido, tipoDocumento, nroDocumento));
    }

    public boolean puedeSerMiembro(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento, ValidadorExterno validadorPertenencia){
        return validadorPertenencia.perteneceMiembro(nombre, apellido, tipoDocumento, nroDocumento);
    }



    public void agregarSector(Sector sector){
        this.sectores.add(sector);
    }

    public Integer getPosSectorActual(Sector sector){
        return this.sectores.indexOf(sector);
    }

    public Double calcularHCdeLaOrg(){
        return this.sectores.stream().mapToDouble(sector->sector.getTotalEmisionMiembros()).sum();
    }

    public void agregarContactoEmail(Miembro miembro, String email){miembro.getContacto().setEmail(email);}
    public void agregarContactoNroCelular(Miembro miembro, String email){miembro.getContacto().setNroCelular(email);}





}
