package TestValidador;

import models.Validador.CriterioValidador;
import models.Validador.ValidacionPorComplejidad;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestValidacionPorComplejidad {

        CriterioValidador validacionPorComplejidad;

        @Before
        public void inicializacion(){
            validacionPorComplejidad = new ValidacionPorComplejidad();
        }
        @Test
        public void contieneMinuscula() throws FileNotFoundException {
            inicializacion();
            assertTrue(validacionPorComplejidad.esValida("CAMAq12123123"));
        }

        @Test
        public void noContieneMinuscula() throws FileNotFoundException {
            inicializacion();
            assertFalse(validacionPorComplejidad.esValida("CAMA12123123"));
        }
        @Test
        public void contieneNumero() throws FileNotFoundException {
            inicializacion();
            assertTrue(validacionPorComplejidad.esValida("CAMAq12123123"));
        }

        @Test
        public void noContieneNumero() throws FileNotFoundException {
            inicializacion();
            assertFalse(validacionPorComplejidad.esValida("CAMAasdasd$"));
        }
        @Test
        public void contieneMayuscula() throws FileNotFoundException {
            inicializacion();
            assertTrue(validacionPorComplejidad.esValida("CAMAq12123123"));
        }

        @Test
        public void noContieneMayuscula() throws FileNotFoundException {
            inicializacion();
            assertFalse(validacionPorComplejidad.esValida("123asdasd$"));
        }
    }

