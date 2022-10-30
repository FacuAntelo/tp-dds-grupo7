package controllers;

import models.Miembro.Miembro;
import models.Miembro.TipoDocumento;
import models.Organizacion.EstadoPeticion;
import models.Organizacion.Organizacion;
import models.Organizacion.Peticion;
import models.Reportes.GeneradorDeReportes;
import models.Sector.Sector;
import models.Usuarios.Usuario;
import models.db.EntityManagerHelper;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioPeticion;
import repositories.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PeticionController {
    RepositorioPeticion repositorioPeticion;
    RepositorioOrganizacion repositorioOrganizacion;
    RepositorioUsuario repositorioUsuario;

    public PeticionController() {
        repositorioPeticion = new RepositorioPeticion();
        repositorioOrganizacion = new RepositorioOrganizacion();
        repositorioUsuario = new RepositorioUsuario();
    }

    public ModelAndView pantallaDePeticion(Request request, Response response) {
        return new ModelAndView(null, "emisionPeticion.hbs");
    }

    public Response guardar(Request request, Response response){
        Usuario usuario = repositorioUsuario.find(Integer.valueOf(request.params("idUsuario")));

        Peticion peticion = new Peticion();
        peticion.setNombre(usuario.getNombre());
        peticion.setApellido(usuario.getApellido());
        peticion.setUsuario(usuario);
//        peticion.setTipoDocumento(usuario.get);
//        peticion.setNumDoc(request.queryParams("nro_docu"));
        peticion.setEmail(usuario.getEmail());
        peticion.setEstadoPeticion(EstadoPeticion.PENDIENTE);

        System.out.println(request.queryParams("idOrganizacion"));

        Organizacion organizacionBuscada = repositorioOrganizacion.buscar(Integer.parseInt(request.queryParams("idOrganizacion")));
        peticion.setOrganizacion(organizacionBuscada);
        System.out.println(request.queryParams("idOrganizacion") + "---------------------------------------------------");
        repositorioPeticion.guardar(peticion);
        response.redirect("/usuario/"+usuario.getId());
        return response;
    }

    public ModelAndView pantallaDePeticiones(Request request, Response response){
        List<Peticion> peticionList = repositorioPeticion.buscarPendientes(Integer.parseInt(request.params("idOrganizacion")));
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(request.params("idOrganizacion")));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("peticiones", peticionList);
            put("organizacion", organizacionBuscado);
        }},"organizacion/peticiones.hbs");
    }

    public Response aceptarPeticion(Request request, Response response) throws InterruptedException {
        Peticion peticion = repositorioPeticion.findByID(Integer.parseInt(request.params("idPeticion")));
        Organizacion organizacion = peticion.getOrganizacion();
        Sector sector = peticion.getSector();

        peticion.setEstadoPeticion(EstadoPeticion.ACEPTADA);
        repositorioPeticion.actualizar(peticion);

        Miembro miembro = new Miembro();
        miembro.setNombre(peticion.getNombre());
        miembro.setApellido(peticion.getApellido());
        miembro.setTipoDocumento(peticion.getTipoDocumento());
        miembro.setNumDoc(peticion.getNumDoc());
        miembro.setUsuario(peticion.getUsuario());

        organizacion.agregarMiembroASector(sector, miembro);


        peticion.getUsuario().agregarMiembro(miembro);
        
        organizacion.rechazarPeticionesPendientesDelUsuario(peticion.getUsuario().getId());

        repositorioOrganizacion.guardar(organizacion);

        response.redirect("/organizacion/"+request.params("idOrganizacion")+"/peticiones");
        return response;
    }

    public Response rechazarPeticion(Request request, Response response) throws InterruptedException {
        Peticion peticion = repositorioPeticion.findByID(Integer.parseInt(request.params("idPeticion")));
        peticion.setEstadoPeticion(EstadoPeticion.RECHAZADA);
        repositorioPeticion.actualizar(peticion);

        response.redirect("/organizacion/"+request.params("idOrganizacion")+"/peticiones");
        return response;
    }

}
