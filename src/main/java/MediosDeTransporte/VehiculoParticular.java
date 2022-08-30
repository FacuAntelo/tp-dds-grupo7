package MediosDeTransporte;

import Combustible.Combustible;
import domain.services.entities.DistanciaAPI;

public class VehiculoParticular extends MediosDeTransporte {
    private TipoVehiculo tipo;
    private Combustible combustible;

    public VehiculoParticular(TipoVehiculo tipo, Combustible combustible,Boolean compartido) {
        this.tipo = tipo;
        this.combustible = combustible;
        this.esCompartido = compartido;
    }

    public TipoVehiculo getTipo() {return tipo;}

    public void setTipo(TipoVehiculo tipo) {this.tipo = tipo;}

    public Combustible getCombustible() {return combustible;}

    public void setCombustible(Combustible combustible) {this.combustible = combustible;}

}
