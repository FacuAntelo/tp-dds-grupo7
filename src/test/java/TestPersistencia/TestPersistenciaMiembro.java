package TestPersistencia;

import org.junit.Before;
import repositories.RepositorioMiembro;
import repositories.RepositorioOrganizacion;

public class TestPersistenciaMiembro {

    RepositorioMiembro repoMiembro = new RepositorioMiembro();


    @Before
    public void init(){
        repoMiembro = new RepositorioMiembro();
    }


}
