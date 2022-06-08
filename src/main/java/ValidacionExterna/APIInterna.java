package ValidacionExterna;

import Miembro.*;
import Organizacion.Organizacion;
import Sector.*;
import java.util.ArrayList;
import java.util.List;

public class APIInterna implements ValidadorExterno{
    private List<String> base;

    public APIInterna(){
        this.base = new ArrayList<>();
        this.base.add("Nacho");
    }

    @Override
    public boolean perteneceMiembro(Persona persona){
        return base.contains(persona.getNombre());
    }

    @Override
    public Sector sectorAlQuePertenece(Persona persona, Sector sector){
        return sector;
        //return new Sector("market", new Organizacion());
    }

    public boolean puedePernecerALaOrganizacion(Persona persona, boolean valorDeRetorno){
        //TODO
        // hace alguna validacion
        return valorDeRetorno;
    }

}
