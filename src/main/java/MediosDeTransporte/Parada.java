package MediosDeTransporte;

import trayecto.Direccion;

public class Parada {
    private String nombre;
    private Direccion direccion;
    private Double distanciaProximaParada;
    private Double distanciaParadaAnterior;

    public Parada(String nombre, Direccion direccion, Double distanciaProximaParada, Double distanciaParadaAnterior) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.distanciaProximaParada = distanciaProximaParada;
        this.distanciaParadaAnterior = distanciaParadaAnterior;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Direccion getDireccion() { return direccion; }

    public void setDireccion(Direccion direccion) { this.direccion = direccion; }

    public Double getDistanciaProximaParada() {
        return distanciaProximaParada;
    }

    public Double getDistanciaParadaAnterior() {
        return distanciaParadaAnterior;
    }
}