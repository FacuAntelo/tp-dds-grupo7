package Organizacion;

import EntidadPersistente.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
public class Clasificacion {
    @Column(name = "clasificacion")
    private String nombre;
}
