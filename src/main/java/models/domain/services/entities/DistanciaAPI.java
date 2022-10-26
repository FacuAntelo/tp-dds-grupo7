package models.domain.services.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;


@Embeddable
public class DistanciaAPI {
    @Column(name = "valor")
    public double valor;
    @Transient
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