package Usuarios;

import Configuracion.Configuracion;

import java.io.IOException;

public class FactorDeEmision {
    double valorFactorEmision;
    String nombreFactor;
    String unidad;

    public FactorDeEmision FactorDeEmision(String nombreFactor) throws IOException {
        Configuracion config = new Configuracion();
        return config.getConfiguracion(nombreFactor);
    }

    public double getValorFactorEmision() {
        return valorFactorEmision;
    }

    public void setFactorEmision(double factorEmision) {
        this.valorFactorEmision = factorEmision;
    }

    public String getNombreFactor() {
        return nombreFactor;
    }

    public void setNombreFactor(String nombreFactor) {
        this.nombreFactor = nombreFactor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}

