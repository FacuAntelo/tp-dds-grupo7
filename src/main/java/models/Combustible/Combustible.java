package models.Combustible;

import models.EntidadPersistente.EntidadPersistente;
import models.Usuarios.FactorDeEmision;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "combustible")
public class Combustible extends EntidadPersistente {
    @Column(name = "combustible")
    private String nombre;
    @OneToOne(cascade = CascadeType.ALL)
    private FactorDeEmision factorEmision;
    @Column(name = "consumo_por_km")
    private int consumoCombustiblexKM;

    public Combustible(String nombre){
        this.nombre = nombre;
    }

    public Combustible() {

    }
}
