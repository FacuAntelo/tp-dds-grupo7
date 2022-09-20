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

    public Localidad() {}

    public int getNumeroLocalidad(){
        return localidad;
    }

    public  void generarReporte(){
        //Busca en la base de datos, todas las organizaciones que pertenezcan a la localidad
        //El test de la query esta en "TestPersistenciaOrganizacion"
        this.setOrganizaciones((List<Organizacion>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT o from Organizacion as o left join o.ubicacion.direccion.localidad as l where l.localidad = :localidad", Organizacion.class)
                .setParameter("localidad", localidad)
                .getResultList());

        //TODO: Buscar todos los registrosHC de las organizaciones.
        this.getOrganizaciones().forEach(organizacion -> this.getRegistrosHC().add(organizacion.getUltimoRegistroHCTotal()));


    }

}
