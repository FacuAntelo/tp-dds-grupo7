package MediosDeTransporte;

import domain.services.entities.DistanciaAPI;

//MEDIO SIN CONTAMINAR
public class Pie extends MediosDeTransporte{


    public Pie(){
        this.setEsCompartido(false);
        this.setTipoTransporte(TipoTransporte.MEDIOS_SIN_CONTAMINAR);
    }

    public double getHC(DistanciaAPI distancia){
        return 0.0;
    }
}
