package controllers;

import models.Miembro.Miembro;
import models.Organizacion.Peticion;
import repositories.RepositorioPeticion;
import spark.Request;
import spark.Response;

public class PeticionController {

    RepositorioPeticion repo = new RepositorioPeticion();

    public Response aceptarPeticion(Request request, Response response){
        Peticion peticion = repo.findByID(Long.valueOf(request.queryParams("idPeticion")));

        Miembro nuevoMiembro = new Miembro(peticion.getNombre(), peticion.getApellido(), peticion.getTipoDocumento(), peticion.getNumDoc());

        response.redirect("/organizacion"+request.queryParams("idOrganizacion"));
        return response;
    }

}
