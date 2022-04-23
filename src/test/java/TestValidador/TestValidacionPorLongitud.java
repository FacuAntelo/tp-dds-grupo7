package TestValidador;
import Validador.CriterioValidador;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestValidacionPorLongitud {
    CriterioValidador validacionPorLongitud = new Validador.ValidacionPorLongitud();

    @Before
    public void inicializacion(){
        validacionPorLongitud = new Validador.ValidacionPorLongitud();
    }
    @Test
    public void longitudCorrecta() throws FileNotFoundException {
        inicializacion();
        assertTrue(validacionPorLongitud.esValida("jose12345"));
    }

    @Test
    public void longitudInvalida() throws FileNotFoundException {
        inicializacion();
        assertFalse(validacionPorLongitud.esValida("utn"));
    }
}
