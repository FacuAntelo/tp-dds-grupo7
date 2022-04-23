package Validador;

import java.io.FileNotFoundException;

public abstract class CriterioValidador {
    public abstract boolean esValida(String clave) throws FileNotFoundException;
}
