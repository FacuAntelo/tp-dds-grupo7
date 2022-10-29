package TestPersistencia;

import models.Miembro.Miembro;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repositories.RepositorioMiembro;
import repositories.RepositorioOrganizacion;

public class TestPersistenciaMiembro {

    RepositorioMiembro repoMiembro = new RepositorioMiembro();


    @Before
    public void init(){
        repoMiembro = new RepositorioMiembro();
    }

    @Test
    public void testUsuarioOrganizacion() {
        int idUsuario = 2;
        int idOrganizacion = 1;

        Miembro miembro = repoMiembro.buscarOrganizacion(idOrganizacion,idUsuario);

        Assert.assertEquals(miembro.getId(),1);

    }


}
