package TestValidador;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import models.Validador.ValidacionCompararContraPeores;
import models.Validador.ValidacionPorComplejidad;
import models.Validador.ValidacionPorLongitud;
import models.Validador.ValidadorDePassword;
import org.junit.Before;
import org.junit.Test;


public class TestValidadorDePassword {
    ValidadorDePassword validadorDePassword;
    ValidacionPorLongitud validacionPorLongitud;
    ValidacionPorComplejidad validacionPorComplejidad;
    ValidacionCompararContraPeores validacionCompararContraPeores;
    @Before
    public void inicializar(){
        validadorDePassword = new ValidadorDePassword();
        validacionPorLongitud = new ValidacionPorLongitud();
        validacionPorComplejidad = new ValidacionPorComplejidad();
        validacionCompararContraPeores = new ValidacionCompararContraPeores();
    }
    @Test
    public void validacionBajoCriterios(){
        inicializar();
        validadorDePassword.agregarCriterio(validacionPorLongitud);
        validadorDePassword.agregarCriterio(validacionPorComplejidad);
        validadorDePassword.agregarCriterio(validacionCompararContraPeores);
        assertTrue(validadorDePassword.esValida("utnGrupo7"));
        assertFalse(validadorDePassword.esValida("utn"));
    }

}
