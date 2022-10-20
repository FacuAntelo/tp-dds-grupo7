package models.MediosDeTransporte;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Servicio {
    @Column(name="nombre_servicio")
    private String nombre;
    public Servicio(){

    }

    public Servicio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}
}