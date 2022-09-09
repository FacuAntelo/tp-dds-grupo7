package Unidad;

import Usuarios.FactorDeEmision;

public abstract class Unidad {

    public abstract void pasarAKG(FactorDeEmision factorDeEmision);
    public abstract void pasarATN(FactorDeEmision factorDeEmision);
    public abstract void pasarAGR(FactorDeEmision factorDeEmision);

    public abstract String getUnidad();
}



