package models.trayecto;


import models.EntidadPersistente.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "direccion")
public class Direccion extends EntidadPersistente {
    @Column(name = "calle")
    private String calle;

    @Column(name = "altura")
    private int altura;

    @ManyToOne(cascade = CascadeType.ALL)
    private Localidad localidad;

    @ManyToOne(cascade = CascadeType.ALL)
    private Provincia provincia;

    public Direccion(String calle, int altura, Localidad localidad, Provincia provincia) {
        this.calle = calle;
        this.altura = altura;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Direccion() {}
}
