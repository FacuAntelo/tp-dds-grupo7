package Unidad;

import Usuarios.FactorDeEmision;

public class KG extends Unidad{
    private TipoDeUnidad unidad = TipoDeUnidad.KG;
    private static KG instancia = null;
    @Override
    public String getUnidad() {
        return unidad.toString();
    }

    public static KG getKG(){
        if(instancia == null){
            return new KG();
        }
        return instancia;
    }

    @Override
    public void pasarAKG(FactorDeEmision factorDeEmision) {
        throw new RuntimeException("Ya esta en KG");
    }

    @Override
    public void pasarATN(FactorDeEmision factorDeEmision) {
        factorDeEmision.setValorFactorEmision(factorDeEmision.getValorFactorEmision()/1000);
        TN tn = TN.getTN();
        factorDeEmision.setTipoUnidad(tn);
    }

    @Override
    public void pasarAGR(FactorDeEmision factorDeEmision) {
        factorDeEmision.setValorFactorEmision(factorDeEmision.getValorFactorEmision()*1000);
        GR gr = GR.getGR();
        factorDeEmision.setTipoUnidad(gr);

    }
}
