package AgenteSectorial;

import Organizacion.Organizacion;
import db.EntityManagerHelper;
import lombok.Getter;
import lombok.Setter;
import trayecto.Localidad;
import trayecto.Provincia;

import java.util.List;

@Getter
@Setter
public class AgenteSectorial {

    private String nombre;
    private String apellido;
    private SectorTerritorial sectorTerritorial;
//    private Provincia provincia;
//    private Localidad localidad;

    public AgenteSectorial(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
//        this.provincia = provincia;
//        this.localidad = localidad;
    }
    public  void generarReporte(SectorTerritorial sectorTerritorial){
        //Busca en la base de datos, todas las organizaciones que pertenezcan a la localidad
        //El test de la query esta en "TestPersistenciaOrganizacion"

//        List<Organizacion> organizaciones = (List<Organizacion>) EntityManagerHelper.getEntityManager()
//                .createQuery("SELECT o from Organizacion as o left join o.ubicacion.direccion.localidad as l where l.localidad = :localidad", Organizacion.class)
//                .setParameter("localidad", localidad)
//                .getResultList();
//
//
//        // sumarias todos los registros, y pondrias eso
//
//        //TODO: Buscar todos los registrosHC de las organizaciones.
//        this.getOrganizaciones().forEach(organizacion -> this.getRegistrosHC().add(organizacion.getUltimoRegistroHCTotal()));
    }
}
