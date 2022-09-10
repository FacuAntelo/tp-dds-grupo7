package MediosDeTransporte;

import Combustible.Combustible;
import domain.services.entities.DistanciaAPI;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Setter
@Getter
public abstract class MediosDeTransporte {
    private Combustible combustible;
    private Boolean esCompartido;
    private TipoTransporte tipoTransporte;
    private Double consumoXKM = 0.1;

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
    public double getValorFactorDeEmision(){
        return this.getCombustible().getFactorEmision().getValorFactorEmision();
    }
}
