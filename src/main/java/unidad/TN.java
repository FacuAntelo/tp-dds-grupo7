package unidad;

import Usuarios.FactorDeEmision;

public class TN extends Unidad{
    private TipoDeUnidad unidad = TipoDeUnidad.TN;
    private static TN instancia = null;

    public static TN getTN(){
        if(instancia == null){
            instancia = new TN();
            return instancia;
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
        throw new RuntimeException("Ya estamos en toneladas");
    }

    @Override
    public void pasarAGR(FactorDeEmision factorDeEmision) {
        factorDeEmision.setValorFactorEmision(factorDeEmision.getValorFactorEmision()/(1000*1000));
        GR gr = GR.getGR();
        factorDeEmision.setTipoUnidad(gr);
    }
}
