package MediosDeTransporte;

import Combustible.Combustible;
import domain.services.entities.DistanciaAPI;

import java.io.IOException;


public abstract class MediosDeTransporte {
    private Combustible combustible;
    private Boolean esCompartido;
    private TipoTransporte tipoTransporte;

    public void setTipoTransporte(TipoTransporte tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }

    public TipoTransporte getTipoTransporte() {
        return tipoTransporte;
    }

    public Boolean getEsCompartido() {
        return esCompartido;
    }

    public void setEsCompartido(Boolean esCompartido) {
        this.esCompartido = esCompartido;
    }

    public double getHC(DistanciaAPI distancia) throws IOException {
        return this.combustible.getFactorEmision().getValorFactorEmision()*distancia.getValor();
    }
}
