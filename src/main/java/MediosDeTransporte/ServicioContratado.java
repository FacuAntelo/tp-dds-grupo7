package MediosDeTransporte;

public class ServicioContratado implements MedioDeTransporte{
    private Servicio servicio;

    public ServicioContratado(Servicio servicio) {
        this.servicio = servicio;
    }

    public Servicio getServicio() {return servicio;}

    public void setServicio(Servicio servicio) {this.servicio = servicio;}
}
