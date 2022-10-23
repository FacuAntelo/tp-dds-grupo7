package TestMiembro;

import models.Miembro.Miembro;
import models.Organizacion.Organizacion;
import models.Sector.Sector;
import models.db.EntityManagerHelper;
import org.junit.Test;
import repositories.RepositorioOrganizacion;

import java.util.List;

public class TestMiembro {
    @Test
    public void obtenerOrganizacionDelMiembro(){
//        RepositorioOrganizacion repo = new RepositorioOrganizacion();
//        List<Miembro> miembros= repo.buscarTodosLosMiembros(1);
//        miembros.forEach(x -> System.out.println(x.getNombre()));

        Organizacion org = EntityManagerHelper.getEntityManager().
                createQuery("select o from Organizacion as o inner join o.sectores as s inner join s.miembros as m where m.id = :id", Organizacion.class).
                setParameter("id", 1).getSingleResult();
        System.out.println(org.getRazonSocial());

    }
}
