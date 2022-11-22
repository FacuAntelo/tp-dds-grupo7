package middlewares;

import models.Miembro.Miembro;
import models.Organizacion.Organizacion;
import repositories.RepositorioMiembro;
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
        List<Organizacion> organizaciones = repositorioOrganizacion.buscarOrganizacionesDelUsuarioQueEsAdministrador(request.session().attribute("id"));
        if(organizaciones.stream().noneMatch(o-> o.getId() == Integer.parseInt(request.params("idOrganizacion")))){
            response.redirect("/prohibido");
        }

        return response;
    }

    public static Response verificarMiembro(Request request, Response response){
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
        RepositorioMiembro repositorioMiembro = new RepositorioMiembro();
        List<Miembro> miembros = repositorioMiembro.buscarMiembrosDelUsuario(request.session().attribute("id"));
        if(miembros.stream().noneMatch(m-> m.getId() == Integer.parseInt(request.params("idMiembro")))){
            response.redirect("/prohibido");
        }

        return response;
    }
}
