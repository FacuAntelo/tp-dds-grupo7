package Combustible;

import EntidadPersistente.EntidadPersistente;
import Usuarios.FactorDeEmision;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.IOException;


@Getter
@Setter
@Entity
@Table(name = "combustible")
public class Combustible extends EntidadPersistente {
    @Column(name = "combustible")
    private String nombre;
    @Transient
    private FactorDeEmision factorEmision;
    @Column(name = "consumo_por_km")
    private int consumoCombustiblexKM;

    public Combustible(String nombre){
        this.nombre = nombre;
    }
}
