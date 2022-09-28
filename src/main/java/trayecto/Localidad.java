package trayecto;
import AgenteSectorial.Territorio;
import Combustible.Combustible;
import EntidadPersistente.EntidadPersistente;
import HuellaDeCarbono.CalculadoraHC;
import Organizacion.Organizacion;
import db.EntityManagerHelper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    // localidad.agregarOrganizacion(organizacion)

}
