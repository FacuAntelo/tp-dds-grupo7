package trayecto;

import domain.services.entities.DistanciaAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Trayecto {
    private List<Tramo> tramos;
    private Direccion puntoInicio;
    private Direccion puntoIpuntoFin;
    private double distanciaTotal;

    public void setPuntoInicio(Direccion puntoInicio) {
        this.puntoInicio = puntoInicio;
        this.tramos = new ArrayList<>();
    }

    public void setPuntoIpuntoFin(Direccion puntoIpuntoFin) {
        this.puntoIpuntoFin = puntoIpuntoFin;
    }

    public void calcularDistanciaTrayecto (){
        distanciaTotal=0;
        tramos.forEach(unTramo -> {
            try {
                distanciaTotal += unTramo.getDistancia().getValor();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void agregarTramo(Tramo tramo){
        tramos.add(tramo);
    }

    public double getDistanciaTotal(){
        return distanciaTotal;
    }

    public double getHCTotalTramos(){
        return this.tramos.stream().mapToDouble(tramo->tramo.getCalculoHC()).sum();
    }
}



