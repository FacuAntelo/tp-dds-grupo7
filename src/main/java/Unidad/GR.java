package Unidad;

import Usuarios.FactorDeEmision;

public class GR extends Unidad {
    private TipoDeUnidad unidad = TipoDeUnidad.GR;
    private static GR instancia = null;

    public static GR getGR(){
        if(instancia == null){
            return new GR();
        }
        return instancia;
    }
    @Override
    public String getUnidad() {
        return unidad.toString();
    }

    @Override
    public void pasarAKG(FactorDeEmision factorDeEmision) {
        factorDeEmision.setValorFactorEmision(factorDeEmision.getValorFactorEmision()/1000);
        KG kg = KG.getKG();
        factorDeEmision.setTipoUnidad(kg);
    }

    @Override
    public void pasarATN(FactorDeEmision factorDeEmision) {
        factorDeEmision.setValorFactorEmision(factorDeEmision.getValorFactorEmision()/(1000*1000));
        TN tn = TN.getTN();
        factorDeEmision.setTipoUnidad(tn);
    }

    @Override
    public void pasarAGR(FactorDeEmision factorDeEmision) {
        throw new RuntimeException("Ya estamos en gramos");
    }
}
