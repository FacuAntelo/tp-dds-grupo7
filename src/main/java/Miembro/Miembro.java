package Miembro;
import trayecto.*;
import Organizacion.Organizacion;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Miembro{
    private List<Trayecto> trayectos;
    private Persona persona;
    private List<Organizacion> organizaciones;



    public Miembro(Persona persona){
        this.persona = persona;
        this.organizaciones = new ArrayList<>();
        this.trayectos = new ArrayList<>();
    }

    public Persona getPersona() {
        return persona;
    }

    public List<Organizacion> getOrganizaciones() {
        return organizaciones;
    }

    public void agregarTrayecto(Trayecto trayecto){
            trayectos.add(trayecto);
     }

    public void agregarOrganizacion(Organizacion organizacion){
        organizaciones.add(organizacion);
    }

    public List<Trayecto> getTrayectos(){
        return trayectos;
    }

    public Double calcularHC(){
        return 0.0;
    }

}
