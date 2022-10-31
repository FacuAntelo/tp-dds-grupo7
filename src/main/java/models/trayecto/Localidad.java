package models.trayecto;
import models.AgenteSectorial.Territorio;
import models.HuellaDeCarbono.CalculadoraHC;
import models.HuellaDeCarbono.RegistroHC;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "localidad")
public class Localidad extends Territorio implements Serializable {

    @Column(name = "nombre_provincia")
    private String nombre;

    public Localidad() {}


    @Override
    public RegistroHC calcularHC() {
        return CalculadoraHC.calcularHCLocalidad(this);
    }

    // localidad.agregarOrganizacion(organizacion)

}
