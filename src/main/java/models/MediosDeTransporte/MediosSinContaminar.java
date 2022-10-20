package models.MediosDeTransporte;

import javax.persistence.*;

@Entity
@DiscriminatorValue("medio_sin_contaminar")
//@Table(name = "medios_sin_contaminar")
public class MediosSinContaminar extends MediosDeTransporte {
    @Column(name = "nombre_medio_sin_contaminar")
    private String nombre;

    public MediosSinContaminar(){
        this.setEsCompartido(false);
        this.setTipoTransporte(TipoTransporte.MEDIOS_SIN_CONTAMINAR);
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public double getValorFactorDeEmision(){
        return 0;
    }
}
