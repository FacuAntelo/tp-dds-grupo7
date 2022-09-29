package MediosDeTransporte;

import domain.services.entities.DistanciaAPI;
import trayecto.Direccion;

import javax.persistence.*;

@Entity
//@Table(name = "servicio_contratado")
@DiscriminatorValue("servicio_contratado")
public class ServicioContratado extends MediosDeTransporte{
    @Embedded
    private Servicio servicio;

    public ServicioContratado(Servicio servicio,boolean esCompartido) {
        this.servicio = servicio;
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
