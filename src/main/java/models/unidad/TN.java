package models.unidad;

import models.HuellaDeCarbono.HuellaDeCarbono;

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
    public void pasarAKG(HuellaDeCarbono huella) {
        huella.setValor(huella.getValor()*1000);
        KG kg = KG.getKG();
        huella.setTipoDeUnidad(kg);
    }

    @Override
    public void pasarATN(HuellaDeCarbono huella) {

    }

    @Override
    public void pasarAGR(HuellaDeCarbono huella) {
        huella.setValor(huella.getValor()*(1000*1000));
        GR gr = GR.getGR();
        huella.setTipoDeUnidad(gr);
    }
}
