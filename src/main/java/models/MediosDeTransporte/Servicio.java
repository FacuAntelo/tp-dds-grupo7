package models.MediosDeTransporte;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
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