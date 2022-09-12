package Organizacion;

import Configuracion.Configuracion;
import Usuarios.FactorDeEmision;
import com.twilio.rest.verify.v2.service.entity.Factor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import Configuracion.Configuracion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosDeActividad {
    private String actividad;
    private String tipoDeConsumo;
    private Double valor;
    private String periodicidad;
    private String periodoDeImputacion;
    private FactorDeEmision factorDeEmision;
}
