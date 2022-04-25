package TestValidador;

import Validador.CriterioValidador;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestValidacionPorComplejidad {

        CriterioValidador validacionPorLongitud = new Validador.ValidacionPorComplejidad();

        @Before
        public void inicializacion(){
            validacionPorLongitud = new Validador.ValidacionPorComplejidad();
        }
        @Test
        public void contieneMinuscula() throws FileNotFoundException {
            inicializacion();
            assertTrue(validacionPorLongitud.esValida("CAMAq12123123"));
        }

        @Test
        public void noContieneMinuscula() throws FileNotFoundException {
            inicializacion();
            assertFalse(validacionPorLongitud.esValida("CAMA12123123"));
        }
        @Test
        public void contieneNumero() throws FileNotFoundException {
            inicializacion();
            assertTrue(validacionPorLongitud.esValida("CAMAq12123123"));
        }

        @Test
        public void noContieneNumero() throws FileNotFoundException {
            inicializacion();
            assertFalse(validacionPorLongitud.esValida("CAMAasdasd$"));
        }
        @Test
        public void contieneMayuscula() throws FileNotFoundException {
            inicializacion();
            assertTrue(validacionPorLongitud.esValida("CAMAq12123123"));
        }

        @Test
        public void noContieneMayuscula() throws FileNotFoundException {
            inicializacion();
            assertFalse(validacionPorLongitud.esValida("123asdasd$"));
        }
    }

