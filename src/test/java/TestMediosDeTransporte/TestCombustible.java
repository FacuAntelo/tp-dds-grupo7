package TestMediosDeTransporte;

import models.db.PersistenciaInicial;
import org.junit.Test;

import java.io.IOException;

public class TestCombustible {
    @Test
    public void persistenciaInicialDecombustible() throws IOException {
        PersistenciaInicial.persistirTodoLoNecesario();
    }
}
