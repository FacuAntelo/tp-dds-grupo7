package TestValidador;
import Validador.CriterioValidador;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestValidacionPorLongitud {
    CriterioValidador validacionPorLongitud = new Validador.ValidacionPorLongitud();

    @Before
    public void inicializacion(){
        validacionPorLongitud = new Validador.ValidacionPorLongitud();
    }
    @Test
    public void longitudCorrecta(){
        inicializacion();
        assertTrue(validacionPorLongitud.esValida("jose12345"));
    }

    @Test
    public void longitudInvalida(){
        inicializacion();
        assertTrue(!validacionPorLongitud.esValida("utn"));
    }
}
