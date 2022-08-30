package MediosDeTransporte;

import Combustible.Combustible;
import com.twilio.rest.supersim.v1.sim.BillingPeriod;
import domain.services.ServicioGeoDDS;
import domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import domain.services.entities.DistanciaAPI;
import trayecto.Direccion;

import java.io.IOException;


public abstract class MediosDeTransporte {
    public Combustible combustible;
    public Boolean esCompartido;

    public double getHC(DistanciaAPI distancia) throws IOException {
        return this.combustible.getFactorEmision().getValorFactorEmision()*distancia.getValor();
    }
}
