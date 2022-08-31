package MediosDeTransporte;

import domain.services.entities.DistanciaAPI;
import trayecto.Direccion;

public class ServicioContratado extends MediosDeTransporte{
    private Servicio servicio;

    public ServicioContratado(Servicio servicio) {
        this.servicio = servicio;
        this.setTipoTransporte(TipoTransporte.SERVICIO_CONTRATADO);
    }

    public Servicio getServicio() {return servicio;}

    public void setServicio(Servicio servicio) {this.servicio = servicio;}

    public double getHC(DistanciaAPI distancia){
        return 0.0;
    }


}
