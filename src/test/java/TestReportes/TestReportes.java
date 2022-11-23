package TestReportes;

import models.Organizacion.Organizacion;
import models.Reportes.GeneradorDeReportes;
import org.junit.Test;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioSectorTerritorial;

import java.io.IOException;

public class TestReportes {
    @Test
    public void test() throws IOException {
        RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
        RepositorioSectorTerritorial repositorioSectorTerritorial = new RepositorioSectorTerritorial();

        GeneradorDeReportes.generarReporteComposicionHCTotalDeOrganizacion(repositorioOrganizacion.buscar(2));
    }
}
