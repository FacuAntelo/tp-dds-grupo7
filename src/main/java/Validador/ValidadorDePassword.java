package Validador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidadorDePassword implements Validable{
    private List<CriterioValidador> criterios;

    public ValidadorDePassword() {
        this.criterios = new ArrayList<>();
    }

    @Override
    public boolean esValida(String clave){
        return criterios.stream().allMatch(criterio -> criterio.esValida(clave));
    }

    public void agregarCriterio(CriterioValidador criterio){
        criterios.add(criterio);
    }
}
