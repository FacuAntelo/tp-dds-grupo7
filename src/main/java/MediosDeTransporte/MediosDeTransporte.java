package MediosDeTransporte;

import Combustible.Combustible;
import domain.services.ServicioGeoDDS;
import domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import domain.services.entities.DistanciaAPI;
import trayecto.Direccion;

import java.io.IOException;


public abstract class MediosDeTransporte {
    ServicioGeoDDS servicio;
    Combustible combustible;

    public DistanciaAPI distancia(Direccion direccionInicial, Direccion direccionFinal) throws IOException {
        servicio = ServicioGeoDDS.getInstance();
        servicio.setAdapter(new ServicioGeoDDSRetrofitAdapter());
        return servicio.distanciaAPI(direccionInicial.getLocalidad().getLocalidad(), direccionInicial.getCalle().getCalle(),direccionInicial.getAltura(),
                direccionFinal.getLocalidad().getLocalidad(),direccionFinal.getCalle().getCalle(),direccionFinal.getAltura());
    }

    public double getHC(DistanciaAPI distancia){
        return this.combustible.getFactorEmision().getValorFactorEmision()*distancia.getValor();
    }
}
