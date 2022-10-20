package controllers;

import repositories.RepositorioOrganizacion;
import spark.Request;
import spark.Response;

public class OrganizacionController {

    public static String traerOrganizacion(Request request, Response response) {
        RepositorioOrganizacion repo = new RepositorioOrganizacion();
        if(repo == null){
            return "no existe organizacion";
        }
        return repo.buscar(Integer.parseInt(request.params("id"))).getRazonSocial();
    }
}
