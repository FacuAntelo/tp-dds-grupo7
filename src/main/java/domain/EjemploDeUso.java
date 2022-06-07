package domain;

import domain.services.geodds.GeoDDSInterface;
import domain.services.geodds.ServicioGeoDDS;
import domain.services.geodds.entities.DistanciaAPI;
import domain.ubicacion.Ubicacion;

import java.io.IOException;

public class EjemploDeUso {
    public static void main(String[] args) throws IOException {
        ServicioGeoDDS servicioGeoDDS = ServicioGeoDDS.getInstance();
        Ubicacion ubicacionOrigen= new Ubicacion().crearUbicacion(1,"suripacha",3);
        Ubicacion ubicacionDestino = new Ubicacion().crearUbicacion(2,"aldosivi",10);

        DistanciaAPI distancia = servicioGeoDDS.distanciaAPI(ubicacionOrigen.getLocalidad(), ubicacionOrigen.getCalle(), ubicacionOrigen.getAltura(),
                ubicacionDestino.getLocalidad(), ubicacionDestino.getCalle(), ubicacionDestino.getAltura());
        System.out.print(distancia.valor);
        System.out.println(distancia.unidad);
    }
}
