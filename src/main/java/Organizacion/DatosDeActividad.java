package Organizacion;

import Configuracion.Configuracion;
import EntidadPersistente.EntidadPersistente;
import Usuarios.FactorDeEmision;
import com.twilio.rest.verify.v2.service.entity.Factor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import Configuracion.Configuracion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    @Transient
    private FactorDeEmision factorDeEmision;
}
