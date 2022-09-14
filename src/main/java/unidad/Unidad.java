package unidad;

import HuellaDeCarbono.HuellaDeCarbono;
import Usuarios.FactorDeEmision;

public abstract class Unidad {

    public abstract void pasarAKG(HuellaDeCarbono huella);
    public abstract void pasarATN(HuellaDeCarbono huella);
    public abstract void pasarAGR(HuellaDeCarbono huella);
    public abstract String getUnidad();
}



