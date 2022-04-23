package Validador;

import java.io.FileNotFoundException;
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
        return criterios.stream().allMatch(criterio -> {
            try {
                return criterio.esValida(clave);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    public void agregarCriterio(CriterioValidador criterio){
        criterios.add(criterio);
    }
}
