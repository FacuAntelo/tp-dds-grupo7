package MediosDeTransporte;

import domain.services.entities.DistanciaAPI;
import domain.services.entities.DistanciaAPI;
import trayecto.Direccion;

public class VehiculoParticular extends MediosDeTransporte {
    private TipoVehiculo tipo;
    private Combustible combustible;

    public VehiculoParticular(TipoVehiculo tipo, Combustible combustible) {
        this.tipo = tipo;
        this.combustible = combustible;
    }

    public TipoVehiculo getTipo() {return tipo;}

    public void setTipo(TipoVehiculo tipo) {this.tipo = tipo;}

    public Combustible getCombustible() {return combustible;}

    public void setCombustible(Combustible combustible) {this.combustible = combustible;}

    public DistanciaAPI distancia(Direccion direccionInicial, Direccion direccionFinal) {
        return null;
    }
}