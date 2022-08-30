package Combustible;

import Usuarios.FactorDeEmision;

import java.io.IOException;

public class Combustible {
    public String nombre;
    public FactorDeEmision factorEmision;

    public Combustible(String nombre){
        this.nombre = nombre;
    }
    public FactorDeEmision getFactorEmision() throws IOException {
        return this.factorEmision;
    }
}
