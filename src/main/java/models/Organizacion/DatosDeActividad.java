package models.Organizacion;

import models.EntidadPersistente.EntidadPersistente;
import models.Usuarios.FactorDeEmision;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "datos_actividad")
public class DatosDeActividad extends EntidadPersistente {
    @Column(name = "actividad")
    private String actividad;

    @Column(name = "tipo_consumo")
    private String tipoDeConsumo;

    @Column(name = "valor")
    private Double valor;

    @Column(name= "periodicidad" )
    private String periodicidad;

    @Column(name = "periodo_de_imputacion")
    private String periodoDeImputacion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_factor_de_emision",referencedColumnName = "id")
    private FactorDeEmision factorDeEmision;

    @Column(name = "calculado")
    private Boolean seCalculo = false;

    public void seCalculo() {
        seCalculo=true;
    }
}
