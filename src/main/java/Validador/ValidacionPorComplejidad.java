package Validador;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;

public class ValidacionPorComplejidad extends CriterioValidador{
    public ValidacionPorComplejidad() {
    }

    @Override
    public boolean esValida(String clave) {

        boolean estadoDeValidacion = false;

        try {
            estadoDeValidacion = clave.chars().anyMatch(Character::isLowerCase) &&
                                 clave.chars().anyMatch(Character::isDigit);
        }catch (Exception exception){
            System.out.println("No se pudo validar por complejidad");
        }
        return estadoDeValidacion;
    }
}
