package Organizacion;

import Configuracion.Configuracion;
import Usuarios.FactorDeEmision;
import com.twilio.rest.verify.v2.service.entity.Factor;

import java.io.IOException;
import java.util.ArrayList;
import Configuracion.Configuracion;

public class DatosDeActividad {
    public String actividad;
    public String tipoDeConsumo;
    public String unidad;
    public Double alcance;
    public Double valor;
    public String periodicidad;
    public String periodoDeImputacion;
    private FactorDeEmision factorDeEmision;

    public FactorDeEmision getFactorDeEmision() throws IOException {
        Configuracion config = new Configuracion();
        this.factorDeEmision= config.getConfiguracion(this.tipoDeConsumo);
        return factorDeEmision;
    }

    public String getActividad() {
        return actividad;
    }

    public String getTipoDeConsumo() {
        return tipoDeConsumo;
    }

    public String getUnidad() {
        return unidad;
    }

    public Double getAlcance() {
        return alcance;
    }

    public Double getValor() {
        return valor;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public String getPeriodoDeImputacion() {
        return periodoDeImputacion;
    }
}
