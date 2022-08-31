package trayecto;

import Organizacion.Organizacion;
import domain.services.entities.DistanciaAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Trayecto {
    private List<Tramo> tramos;
    private Direccion puntoInicio;
    private Direccion puntoFin;
    private double distanciaTotal;

    public List<Tramo> getTramos() {
        return tramos;
    }

    public Trayecto(Direccion inicial, Direccion ffinal){
        this.puntoInicio= inicial;
        this.puntoFin = ffinal;
        this.tramos = new ArrayList<>();
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



