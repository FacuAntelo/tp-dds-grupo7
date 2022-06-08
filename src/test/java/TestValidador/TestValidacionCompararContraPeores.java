package TestValidador;
import Validador.CriterioValidador;
import Validador.ValidacionCompararContraPeores;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestValidacionCompararContraPeores {
    CriterioValidador validacionCompararContraPeores = new Validador.ValidacionCompararContraPeores();

    @Before
    public void inicializacion(){
        validacionCompararContraPeores = new ValidacionCompararContraPeores();
    }
    @Test
    public void contraseniaValida() throws FileNotFoundException {
        inicializacion();
        assertTrue(validacionCompararContraPeores.esValida("jose12345"));
    }

    @Test
    public void contraseniaInvalida() throws FileNotFoundException {
        inicializacion();
        assertFalse(validacionCompararContraPeores.esValida("hola"));
    }
}
