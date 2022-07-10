package Usuarios;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FactorDeEmision {
    double factorEmision;
    String nombreFactor;
    String unidad;

    public FactorDeEmision(String nombreFactor,double factorEmision, String unidad) {
        this.factorEmision = factorEmision;
        this.nombreFactor = nombreFactor;
        this.unidad = unidad;
    }

    public double getFactorEmision() {
        return factorEmision;
    }

    public void setFactorEmision(double factorEmision) {
        this.factorEmision = factorEmision;
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

