package MediosDeTransporte;

import Combustible.Combustible;
import domain.services.entities.DistanciaAPI;

import javax.persistence.*;

@Entity
@DiscriminatorValue("vehiculo_particular")
//@Table(name = "vehiculo_particular")
public class VehiculoParticular extends MediosDeTransporte {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vehiculo_particular")
    private TipoVehiculo tipo;


    public VehiculoParticular(TipoVehiculo tipo, Combustible combustible,Boolean compartido) {
        this.tipo = tipo;
        this.setCombustible(combustible);
        this.setEsCompartido(compartido);
        this.setTipoTransporte(TipoTransporte.VEHICULO_PARTICULAR);
    }

    public VehiculoParticular() {

    }

    public TipoVehiculo getTipo() {return tipo;}

    public void setTipo(TipoVehiculo tipo) {this.tipo = tipo;}

}
