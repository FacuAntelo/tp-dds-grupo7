package Organizacion;

import EntidadPersistente.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;
import trayecto.Direccion;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "ubicacion")
public class Ubicacion extends EntidadPersistente {
    @Transient
    private Direccion direccion;
    @Column(name = "codigo_postal")
    private Integer codigoPostal;

}
