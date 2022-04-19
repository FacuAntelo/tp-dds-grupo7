package Validador;

import java.util.ArrayList;
import java.util.List;

public class ValidadorDePassword {
    private List<CriterioValidador> criterios;

    public ValidadorDePassword(List<CriterioValidador> criterios) {
        this.criterios = new ArrayList<>();
    }

    public boolean esValida(String clave){
        return true;
    }

    public void agregarCriterio(CriterioValidador criterio){
        criterios.add(criterio);
    }
}
