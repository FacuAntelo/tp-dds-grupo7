package models.MediosDeTransporte;

import lombok.Getter;
import lombok.Setter;
import models.Combustible.Combustible;
import models.domain.services.entities.DistanciaAPI;

import javax.persistence.*;

@Entity
//@Table(name = "servicio_contratado")
@DiscriminatorValue("servicio_contratado")
@Getter
@Setter
public class ServicioContratado extends MediosDeTransporte{
    @Embedded
    private Servicio servicio;

    public ServicioContratado(Servicio servicio,boolean esCompartido) {
        this.servicio = servicio;
        this.setTipoTransporte(TipoTransporte.SERVICIO_CONTRATADO);
        this.setEsCompartido(esCompartido);
    }

    public ServicioContratado(Servicio servicio, Combustible combustible, boolean esCompartido) {
        this.servicio = servicio;
        this.setCombustible(combustible);
        this.setTipoTransporte(TipoTransporte.SERVICIO_CONTRATADO);
        this.setEsCompartido(esCompartido);
    }

    public ServicioContratado() {

    }

    public Servicio getServicio() {return servicio;}

    public void setServicio(Servicio servicio) {this.servicio = servicio;}

    public double getHC(DistanciaAPI distancia){
        return 0.0;
    }


}
