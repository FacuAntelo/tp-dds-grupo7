package helpers;

import models.Usuarios.Permiso;
import spark.Request;
import spark.Response;

public class PermisoHelper {

    public static Boolean usuarioTienePermisos(Request request, Permiso ... permisos){

        return UsuarioLogueadoHelper
                .usuarioLogueado(request)
                .getRol()
                .tenesTodosLosPermisos(permisos);
    }
}
