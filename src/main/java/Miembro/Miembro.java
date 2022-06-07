package Miembro;

import Organizacion.Organizacion;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Miembro{
    //private List<Trayecto> trayectos;
    private Persona persona;
    private List<Organizacion> organizaciones;

    public Miembro(Persona persona){
        this.persona = persona;
        this.organizaciones = new ArrayList<>();
    }

    public List<Organizacion> getOrganizaciones() {
        return organizaciones;
    }

    /*public void agregarTrayecto(Trayecto trayecto){
            trayectos.add(trayecto);
     }*/

    public void agregarOrganizacion(Organizacion organizacion){
        organizaciones.add(organizacion);
    }


}
