package middlewares;

import models.Organizacion.Organizacion;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioUsuario;
import spark.Request;
import spark.Response;

import java.util.List;

public class AuthMiddleware {

    public static Response verificarSesion(Request request, Response response){
        if(request.session().isNew() || request.session().attribute("id") == null){
            response.redirect("/login");
        }
        return response;
    }

    public static  Response verificarOrganizacion(Request request, Response response){
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
        RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
        System.out.println("srarasa");
        List<Organizacion> organizaciones = repositorioOrganizacion.buscarOrganizacionesDelUsuario(request.session().attribute("id"));
        if(organizaciones.stream().noneMatch(o-> o.getId() == Integer.parseInt(request.params("idOrganizacion")))){
            System.out.println("srarasa");
            response.redirect("/prohibido");
        }

        return response;
    }
}
