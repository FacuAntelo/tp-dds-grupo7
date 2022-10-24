package controllers;

import models.Miembro.Miembro;
import models.Miembro.TipoDocumento;
import models.Organizacion.EstadoPeticion;
import models.Organizacion.Organizacion;
import models.Organizacion.Peticion;
import models.Reportes.GeneradorDeReportes;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioPeticion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class PeticionController {

    RepositorioPeticion repositorioPeticion;
    RepositorioOrganizacion repositorioOrganizacion;

    public PeticionController() {
        repositorioPeticion = new RepositorioPeticion();
        repositorioOrganizacion = new RepositorioOrganizacion();
    }

    public ModelAndView pantallaDePeticion(Request request, Response response) {
        return new ModelAndView(null, "emisionPeticion.hbs");
    }

    public Response guardar(Request request, Response response){
        Peticion peticion = new Peticion();
        peticion.setNombre(request.queryParams("nombre"));
        peticion.setApellido(request.queryParams("apellido"));
//        peticion.setTipoDocumento(request.queryParams("tipo_documento"));
        peticion.setNumDoc(request.queryParams("nro_docu"));
        peticion.setEmail(request.queryParams("email"));
        peticion.setEstadoPeticion(EstadoPeticion.PENDIENTE);

        Organizacion organizacionBuscada = repositorioOrganizacion.buscar(Integer.parseInt(request.queryParams("organizacion")));
        peticion.setOrganizacion(organizacionBuscada);

        repositorioPeticion.guardar(peticion);
        response.redirect("/login");
        return response;
    }

    public ModelAndView mostrar(Request request, Response response){
        List<Peticion> peticionList = repositorioPeticion.buscarTodos(Integer.parseInt(request.params("idOrganizacion")));
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(request.params("idOrganizacion")));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("peticiones", peticionList);
            put("organizacion", organizacionBuscado);
        }},"organizacion/peticiones.hbs");
    }

    //TODOS LOS DATOS SE OBTIENEN DE LOS TEMPLATES, NO POR RUTA
    public Response aceptarPeticion(Request request, Response response){
        Peticion peticion = repositorioPeticion.findByID(Long.valueOf(request.queryParams("idPeticion")));

        Miembro nuevoMiembro = new Miembro(peticion.getNombre(), peticion.getApellido(), peticion.getTipoDocumento(), peticion.getNumDoc());

        response.redirect("/organizacion"+request.queryParams("idOrganizacion"));
        return response;
    }

}
