package MediosDeTransporte;

import domain.services.entities.DistanciaAPI;

//MEDIO SIN CONTAMINAR
public class Pie extends MediosDeTransporte{


    public Pie(){
        super.esCompartido = false;
    }

    public double getHC(DistanciaAPI distancia){
        return 0.0;
    }
}
