package domain.services.entities;

import static domain.services.entities.Unidades.KM;

public class DistanciaAPI {
    public double valor;
    public Unidades unidad;

    public DistanciaAPI(){
        this.unidad = KM;
    }
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setUnidad(String unidad) {
        this.unidad = Unidades.valueOf(unidad);
    }

    public Unidades getUnidad() {
        return unidad;
    }

}
