package MediosDeTransporte;

import domain.services.entities.DistanciaAPI;
import trayecto.Direccion;

public class ServicioContratado extends MediosDeTransporte{
    private Servicio servicio;

    public ServicioContratado(Servicio servicio) {
        this.servicio = servicio;
    }

    public Servicio getServicio() {return servicio;}

    public void setServicio(Servicio servicio) {this.servicio = servicio;}

    /*public DistanciaAPI distancia(Direccion direccionInicial, Direccion direccionFinal) {
        return new DistanciaAPI();
        //Cambiar esto
    }*/
}
