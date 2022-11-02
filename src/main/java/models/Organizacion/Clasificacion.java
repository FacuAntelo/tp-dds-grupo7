package models.Organizacion;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
public class Clasificacion {
    @Column(name = "clasificacion")
    private String nombre;

    public Clasificacion(){}
    public Clasificacion(String nombre){
        this.nombre = nombre;
    }
}
