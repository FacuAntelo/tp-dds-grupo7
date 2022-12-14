package models.domain;

import models.domain.services.ServicioGeoDDS;
import models.domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import models.domain.services.entities.DistanciaAPI;
import models.domain.ubicacion.Ubicacion;

import java.io.IOException;

public class EjemploDeUso {
    public static void main(String[] args) throws IOException {
        ServicioGeoDDS servicioGeoDDS = ServicioGeoDDS.getInstance();
        servicioGeoDDS.setAdapter(new ServicioGeoDDSRetrofitAdapter());
        Ubicacion ubicacionOrigen = new Ubicacion().crearUbicacion(54, "suripacha", 3);
        Ubicacion ubicacionDestino = new Ubicacion().crearUbicacion(2, "aldosivi", 10);

        DistanciaAPI distancia = servicioGeoDDS.distanciaAPI(ubicacionOrigen.getLocalidad(), ubicacionOrigen.getCalle(), ubicacionOrigen.getAltura(),
                ubicacionDestino.getLocalidad(), ubicacionDestino.getCalle(), ubicacionDestino.getAltura());
        System.out.print(distancia.valor);
        System.out.println(distancia.unidad);
    }
}
