package models.Organizacion;

import models.EntidadPersistente.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;
import models.trayecto.Direccion;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "ubicacion")
public class Ubicacion extends EntidadPersistente {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "id_direccion")
    private Direccion direccion;
    @Column(name = "codigo_postal")
    private Integer codigoPostal;
}
