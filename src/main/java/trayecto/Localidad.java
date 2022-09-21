package trayecto;
import AgenteSectorial.SectorTerritorial;
import Combustible.Combustible;
import HuellaDeCarbono.CalculadoraHC;
import Organizacion.Organizacion;
import db.EntityManagerHelper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.List;

//
@Embeddable
@Getter
@Setter
public class Localidad extends SectorTerritorial {
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
