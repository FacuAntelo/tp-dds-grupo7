package TestValidador;
import static org.junit.Assert.assertTrue;

import Validador.*;
import org.junit.Before;
import org.junit.Test;


public class TestValidadorDePassword {
    ValidadorDePassword validadorDePassword;
    ValidacionPorLongitud validacionPorLongitud;
    @Before
    public void inicializar(){
        validadorDePassword = new Validador.ValidadorDePassword();
        validacionPorLongitud = new ValidacionPorLongitud();
    }
    @Test
    public void validacionBajoUnCriterio(){
        inicializar();
        validadorDePassword.agregarCriterio(validacionPorLongitud);
        assertTrue(!validadorDePassword.esValida("utn"));
    }

}
