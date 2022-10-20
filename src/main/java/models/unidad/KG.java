package models.unidad;

import models.HuellaDeCarbono.HuellaDeCarbono;

public class KG extends Unidad{
    private TipoDeUnidad unidad = TipoDeUnidad.KG;
    private static KG instancia = null;
    @Override
    public String getUnidad() {
        return unidad.toString();
    }

    public static KG getKG(){
        if(instancia == null){
            instancia = new KG();
            return instancia;
        }
        return instancia;
    }

    @Override
    public void pasarAKG(HuellaDeCarbono huella) {

    }

    @Override
    public void pasarATN(HuellaDeCarbono huella) {
        huella.setValor(huella.getValor()/(1000));
        TN tn = TN.getTN();
        huella.setTipoDeUnidad(tn);
    }

    @Override
    public void pasarAGR(HuellaDeCarbono huella) {
        huella.setValor(huella.getValor()*(1000));
        GR gr = GR.getGR();
        huella.setTipoDeUnidad(gr);
    }
}
