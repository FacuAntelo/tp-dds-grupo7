package TestCalculadoraHC;

import models.HuellaDeCarbono.CalculadoraHC;
import models.HuellaDeCarbono.RegistroHC;
import models.Organizacion.Organizacion;
import org.junit.Test;
import repositories.RepositorioOrganizacion;

public class TestCalculadoraHC {
    @Test
    public void calculadora(){
        RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
        Organizacion organizacion = repositorioOrganizacion.buscar(1);

        RegistroHC registroHC = organizacion.calcularHC();
        System.out.println(registroHC);
        repositorioOrganizacion.guardar(organizacion);
    }
}
