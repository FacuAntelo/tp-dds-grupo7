package Sector;

import Organizacion.Organizacion;
import Miembro.*;
import java.util.ArrayList;
import java.util.List;

public class Sector {
    private String nombre;
    private List<Persona> miembros;
    private Organizacion organizacion;

    public Sector(String nombre, Organizacion organizacion){
        this.nombre = nombre;
        this.organizacion = organizacion;
        this.miembros = new ArrayList<>();
    }

    public void agregarMiembro(Persona miembro){
        this.miembros.add(miembro);
    }

    public List<Persona> getMiembros() {
        return miembros;
    }
}
