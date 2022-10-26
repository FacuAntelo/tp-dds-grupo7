package TestUsuario;

import models.Organizacion.Organizacion;
import models.Usuarios.Usuario;
import models.db.EntityManagerHelper;
import org.junit.Test;
import repositories.RepositorioUsuario;

import java.util.List;

public class TestUsuario {
    @Test
    public void obtenerUsuario(){

        Usuario u = new RepositorioUsuario().buscarPorNombreUsuarioYContrasenia("a", "123456789Tp");
        System.out.println(u.getNombreDeUsuario());
    }
}
