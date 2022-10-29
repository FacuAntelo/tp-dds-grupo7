package models.Sector;

import models.EntidadPersistente.EntidadPersistente;
import models.Organizacion.Organizacion;
import lombok.Getter;
import lombok.Setter;
import models.Miembro.Miembro;
import models.Organizacion.Peticion;
import models.trayecto.Tramo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Table(name = "sector")
public class Sector extends EntidadPersistente {
    @Column(name = "nombre_sector")
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sector_id",referencedColumnName = "id")
    private List<Miembro> miembros;

    @OneToMany(fetch =FetchType.LAZY, mappedBy = "sector",cascade = CascadeType.REFRESH )
    private List<Peticion> peticiones;

    public Sector(String nombre, Organizacion organizacion){
        this.nombre = nombre;

        this.miembros = new ArrayList<>();
    }

    public Sector() {

    }

    public List<Tramo> getTramosMiembros(){
        return this.getMiembros().stream().flatMap(miembro-> miembro.getTramos().stream()).collect(Collectors.toList());
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

}
