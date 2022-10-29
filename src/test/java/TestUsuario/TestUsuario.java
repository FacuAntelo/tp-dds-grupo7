package TestUsuario;

import models.Miembro.TipoDocumento;
import models.Organizacion.Organizacion;
import models.Usuarios.Usuario;
import models.db.EntityManagerHelper;
import org.junit.Test;
import repositories.RepositorioUsuario;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestUsuario {
    @Test
    public void obtenerUsuario(){

        Usuario u = new RepositorioUsuario().buscarPorNombreUsuarioYContrasenia("a", "123456789Tp");
        System.out.println(u.getNombreDeUsuario());
    }

    @Test
    public void listaDeTipoDocumento(){
        TipoDocumento td= Enum.valueOf(TipoDocumento.class,"DNI");
        System.out.println(td.getClass());

        Object[] possibleValues = td.getDeclaringClass().getEnumConstants();
        List<TipoDocumento> tipoDocumentoList = Arrays.asList(TipoDocumento.values());
        List<String> s = tipoDocumentoList.stream().map(x -> x.name()).collect(Collectors.toList());
        List<String> arrays= Arrays.stream(TipoDocumento.values()).map(x-> x.name()).collect(Collectors.toList());
        System.out.println(arrays.get(0).getClass());
    }
}
