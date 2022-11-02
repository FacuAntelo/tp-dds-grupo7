package models.MediosDeTransporte;

import models.EntidadPersistente.EntidadPersistente;
import models.trayecto.Direccion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Entity
@Table(name = "linea")
public class Linea extends EntidadPersistente {
    @Column(name= "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_linea",referencedColumnName = "id")
    private List<Parada> paradas;

    public Linea(String nombre) {
        this.nombre = nombre;
        this.paradas = new ArrayList<Parada>();
    }

    public Linea() {
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public List<Parada> getParadas() {return paradas;}

    public void setParada(Parada parada) {
        this.paradas.add(parada);
    }

    public Parada obtenerParadaDeLaDireccion(Direccion unaDireccion){
        return paradas.stream().filter(parada->parada.getDireccion() == unaDireccion).collect(Collectors.toList()).get(0);

    }


}