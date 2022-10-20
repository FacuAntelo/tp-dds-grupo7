package models.trayecto;
import models.AgenteSectorial.Territorio;
import models.HuellaDeCarbono.CalculadoraHC;
import models.HuellaDeCarbono.RegistroHC;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "localidad")
public class Localidad extends Territorio {
    @Column(name = "localidad")
    private int localidad;

    public Localidad(int localidad) {this.localidad = localidad;}

    // lista de organizaciones

    public Localidad() {}

    public int getNumeroLocalidad(){
        return localidad;
    }

    @Override
    public RegistroHC calcularHC() {
        return CalculadoraHC.calcularHCLocalidad(this);
    }

    // localidad.agregarOrganizacion(organizacion)

}
