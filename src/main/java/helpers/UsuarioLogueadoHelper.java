package helpers;

import models.Usuarios.Usuario;
import models.db.EntityManagerHelper;
import spark.Request;

public class UsuarioLogueadoHelper {

    public static Usuario usuarioLogueado(Request request){
        return EntityManagerHelper
                .getEntityManager()
                .find(Usuario.class, request.session().attribute("id"));
    }
}
