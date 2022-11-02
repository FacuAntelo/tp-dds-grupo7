package models.MediosDeTransporte;

import models.EntidadPersistente.EntidadPersistente;
import models.trayecto.Direccion;

import javax.persistence.*;

@Entity
@Table(name = "parada")
public class Parada extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;
    @Column(name = "distancia_proxima_parada")
    private Double distanciaProximaParada;
    @Column(name = "distancia_parada_anterior")
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