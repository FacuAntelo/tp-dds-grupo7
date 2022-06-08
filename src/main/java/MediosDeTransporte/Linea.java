package MediosDeTransporte;

import java.util.ArrayList;
import java.util.List;

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

    public Linea agregarParada(Parada parada) {
        this.paradas.add(parada);
        return this;
    }


}