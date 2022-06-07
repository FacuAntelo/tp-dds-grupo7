package MediosDeTransporte;

public class VehiculoParticular implements MedioDeTransporte{
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

}