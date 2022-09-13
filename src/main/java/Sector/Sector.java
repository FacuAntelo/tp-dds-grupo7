package Sector;

import EntidadPersistente.EntidadPersistente;
import Organizacion.Organizacion;
import Miembro.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sector")
public class Sector extends EntidadPersistente {
    @Column(name = "nombre_sector")
    private String nombre;

    @OneToMany
    @JoinColumn(name = "sector_id",referencedColumnName = "id")
    private List<Miembro> miembros;


    public Sector(String nombre, Organizacion organizacion){
        this.nombre = nombre;

        this.miembros = new ArrayList<>();
    }

    public void agregarMiembro(Miembro miembro){
        this.miembros.add(miembro);
    }

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public String getNombre() {
        return nombre;
    }

    public double getTotalEmisionMiembros(){
        return this.miembros.stream().mapToDouble(miembro->miembro.calcularHC()).sum();
    }
}
