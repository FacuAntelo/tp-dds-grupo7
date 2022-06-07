package MediosDeTransporte;

import java.util.List;

public class Linea {
    private String nombre;
    private List<Parada> paradas;

    public Linea(String nombre, List<Parada> paradas) {
        this.nombre = nombre;
        this.paradas = paradas;
    }
    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public List<Parada> getParadas() {return paradas;}

    public void setParadas(List<Parada> paradas) {this.paradas = paradas;}
}