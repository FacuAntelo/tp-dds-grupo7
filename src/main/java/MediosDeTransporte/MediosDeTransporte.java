package MediosDeTransporte;

import domain.services.ServicioGeoDDS;
import domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import domain.services.entities.DistanciaAPI;
import trayecto.Direccion;

import java.io.IOException;


public abstract class MediosDeTransporte {
    ServicioGeoDDS servicio;

    public DistanciaAPI distancia(Direccion direccionInicial, Direccion direccionFinal) throws IOException {
        servicio = new ServicioGeoDDS();
        servicio.setAdapter(new ServicioGeoDDSRetrofitAdapter());
        return servicio.distanciaAPI(direccionInicial.getLocalidad().getLocalidad(), direccionInicial.getCalle().getCalle(),direccionInicial.getAltura(),
                direccionFinal.getLocalidad().getLocalidad(),direccionFinal.getCalle().getCalle(),direccionFinal.getAltura());
    }
}
