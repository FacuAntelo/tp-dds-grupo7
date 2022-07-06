package TestMiembro;

import Miembro.*;
import Organizacion.Organizacion;
import Sector.Sector;
import ValidacionExterna.APIInterna;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestAltaMiembro {
    //Instancias de miembro
    Organizacion organizacion = new Organizacion();
    Sector sector = new Sector("market", organizacion);

    //Base para comparar ficticia
    APIInterna validadorExterno = new APIInterna();

    @Before
    public void inicializacion(){
        organizacion.agregarSector(sector);
    }
    @Test
    public void altaMiembro(){
        inicializacion();
        organizacion.recibePeticion ("Nacho", "n", TipoDocumento.DNI, "05072022", validadorExterno);
        assertTrue(validadorExterno.getSectores().get(0).getMiembros().stream().anyMatch(unmiembro -> unmiembro.getNombre() == "Nacho"));

    }
}
