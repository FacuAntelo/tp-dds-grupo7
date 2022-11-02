package models.domain.services.entities;

import javax.persistence.*;


@Embeddable
public class DistanciaAPI {
    @Column(name = "valor de la distancia")
    public double valor;

    @Enumerated(EnumType.STRING)
    public Unidades unidad;

    public DistanciaAPI(){
        this.unidad = Unidades.KM;
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
