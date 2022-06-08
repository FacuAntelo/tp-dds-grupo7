package MediosDeTransporte;

import trayecto.Direccion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Linea {
    private String nombre;
    private List<Parada> paradas;

    public Linea(String nombre) {
        this.nombre = nombre;
        this.paradas = new ArrayList<Parada>();
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