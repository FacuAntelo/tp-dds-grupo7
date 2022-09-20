package trayecto;

import AgenteSectorial.SectorTerritorial;
import Organizacion.Organizacion;
import db.EntityManagerHelper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@Getter
@Setter
public class Provincia extends SectorTerritorial {
    @Column(name = "provincia")
    private String provincia;

    public Provincia(String provincia) {
        this.provincia = provincia;
    }

    public Provincia() {}

    public  void generarReporte(){
        //Busca en la base de datos, todas las organizaciones que pertenezcan a la provincia
        this.setOrganizaciones((List<Organizacion>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT o from Organizacion as o left join o.ubicacion.direccion.provincia as l where l.provincia = :provincia", Organizacion.class)
                .setParameter("provincia", provincia)
                .getResultList());

        //TODO: Buscar todos los registrosHC de las organizaciones. Igual que localidad
        this.getOrganizaciones().forEach(organizacion -> this.getRegistrosHC().add(organizacion.getUltimoRegistroHCTotal()));


    }
}
