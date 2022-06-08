package trayecto;

import domain.services.entities.DistanciaAPI;

import java.io.IOException;
import java.util.List;

public class Trayecto {
    private List<Tramo> tramos;
    private Direccion puntoInicio;
    private Direccion puntoIpuntoFin;
    private double distanciaTotal;

    public void calcularDistanciaTrayecto (){
        tramos.forEach(unTramo -> {
            try {
                distanciaTotal += unTramo.getDistancia().getValor();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}



