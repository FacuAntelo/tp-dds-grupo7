package TestMediosDeTransporte;

import models.db.PersistenciaInicial;
import org.junit.Test;

public class TestCombustible {
    @Test
    public void persistenciaInicialDecombustible(){
        PersistenciaInicial.persistirCombustibles();
    }
}
