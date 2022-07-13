package Combustible;

import Usuarios.FactorDeEmision;

import java.io.IOException;

public abstract class Combustible {
    public FactorDeEmision factorEmision;

    public abstract void cargarFactorEmision() throws IOException;

    public FactorDeEmision getFactorEmision() {
        return this.factorEmision;
    }
}
