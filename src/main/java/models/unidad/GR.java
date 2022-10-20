package models.unidad;

import models.HuellaDeCarbono.HuellaDeCarbono;

public class GR extends Unidad {
    private TipoDeUnidad unidad = TipoDeUnidad.GR;
    private static GR instancia = null;

    public static GR getGR(){
        if(instancia == null){
            instancia = new GR();
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
        huella.setValor(huella.getValor()/1000);
        KG kg = KG.getKG();
        huella.setTipoDeUnidad(kg);
    }

    @Override
    public void pasarATN(HuellaDeCarbono huella) {
        huella.setValor(huella.getValor()/(1000*1000));
        TN tn = TN.getTN();
        huella.setTipoDeUnidad(tn);
    }

    @Override
    public void pasarAGR(HuellaDeCarbono huella) {
    }
}
