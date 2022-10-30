package models.trayecto;

import lombok.Getter;
import lombok.Setter;
import models.EntidadPersistente.EntidadPersistente;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "trayecto")
public class Trayecto extends EntidadPersistente {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "trayecto_id", referencedColumnName = "id")
    private List<Tramo> tramos;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "direccion_inicio_id",referencedColumnName = "id")
    private Direccion puntoInicio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "direccion_final_id",referencedColumnName = "id")
    private Direccion puntoFin;

    @Column(name = "distancia_total")
    private double distanciaTotal;

    public Trayecto() {

    }
    public Trayecto(Direccion inicial, Direccion ffinal){
        this.puntoInicio= inicial;
        this.puntoFin = ffinal;
        this.tramos = new ArrayList<>();
    }


    public List<Tramo> getTramos() {
        return tramos;
    }


    public void setPuntoInicio(Direccion puntoInicio) {
        this.puntoInicio = puntoInicio;
        this.tramos = new ArrayList<>();
    }

    public void setPuntoIpuntoFin(Direccion puntoIpuntoFin) {
        this.puntoFin = puntoIpuntoFin;
    }

//    public void calcularDistanciaTrayecto (){
//        distanciaTotal=0;
//        tramos.forEach(unTramo -> {
//            try {
//                distanciaTotal += unTramo.getDistancia().getValor();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//    }

    public void agregarTramo(Tramo tramo){
        tramos.add(tramo);
    }

    public double getDistanciaTotal(){
        return distanciaTotal;
    }

    public double getHCTotalTramos(){
        return this.tramos.stream().mapToDouble(tramo-> {
            try {
                return tramo.getCalculoHC();
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }).sum();
    }
}



