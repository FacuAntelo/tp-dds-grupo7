package controllers.userlog;

import controllers.ReporteController;
import models.Miembro.Miembro;
import models.Organizacion.*;
import models.Reportes.GeneradorDeReportes;
import models.Sector.Sector;
import models.Usuarios.Usuario;
import models.db.EntityManagerHelper;
import repositories.RepositorioMiembro;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioPeticion;
import repositories.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;

public class UsuarioController {
    RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
    RepositorioPeticion repositorioPeticion = new RepositorioPeticion();
    RepositorioMiembro repositorioMiembro = new RepositorioMiembro();

    public ModelAndView pantallaHome(Request request, Response response) {
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));

        List<Organizacion> organizacionList = repositorioOrganizacion.buscarOrganizacionesDeUsuario(Integer.valueOf(idUsuario));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizaciones", organizacionList);
            put("usuario", usuario);
        }},"homeUsuario.hbs");
    }

    public ModelAndView mostrar (Request request, Response response){
        RepositorioOrganizacion repositorioOrganizacion= new RepositorioOrganizacion();

        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));

        if(organizacionBuscado==null){
            response.redirect("/login");
        }
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);
            put("reportes", GeneradorDeReportes.reporteDeHCdeSectores(organizacionBuscado));
        }},"homeOrganizacion.hbs");
    }


    public String mostrarOrganizaciones(Request request, Response response) {
        List<Organizacion> organizacionList = repositorioOrganizacion.buscarOrganizacionesDeUsuario(Integer.valueOf(request.queryParams("idUsuario")));
        return null;
    }

    public ModelAndView pantallaDePeticion(Request request, Response response) {
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));
        List<Organizacion> organizaciones = repositorioOrganizacion.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizaciones", organizaciones);
            put("usuario", usuario);
        }},"emisionPeticion.hbs");
    }

    public Response pantallaDePeticionSector(Request request, Response response){
        response.redirect("/usuario/" + request.params("idUsuario") + "/peticion/organizacion/"+ request.queryParams("idOrganizacion"));
        return response;
    }


    public ModelAndView pantallaDePeticionSectores(Request request, Response response) {
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));
        System.out.println(request.params("idOrganizacion") + "---- 415646545465 ");

        Integer idOrganizacion= Integer.valueOf(request.params("idOrganizacion"));

        Organizacion organizacion = repositorioOrganizacion.buscar(idOrganizacion);

        System.out.println("HOLAA");

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacion);
            put("usuario", usuario);
            put("sectores",organizacion.getSectores());
        }},"emisionPeticionSector.hbs");
    }
    public Response guardarPeticion(Request request, Response response){
        Usuario usuario = repositorioUsuario.find(Integer.valueOf(request.params("idUsuario")));
        Organizacion organizacionBuscada = repositorioOrganizacion.buscar(Integer.parseInt(request.params("idOrganizacion")));
        Sector sectorElegido = repositorioOrganizacion.buscarSector(organizacionBuscada.getId(), Integer.parseInt(request.queryParams("idSector")));

        Peticion peticion = new Peticion();
        peticion.setNombre(usuario.getNombre());
        peticion.setApellido(usuario.getApellido());
        peticion.setUsuario(usuario);
        peticion.setTipoDocumento(usuario.getTipoDocumento());
        peticion.setNumDoc(usuario.getNumeroDocumento());
        peticion.setEmail(usuario.getEmail());
        peticion.setEstadoPeticion(EstadoPeticion.PENDIENTE);
        peticion.setSector(sectorElegido);

        peticion.setOrganizacion(organizacionBuscada);
        organizacionBuscada.agregarPeticion(peticion);

        repositorioOrganizacion.guardar(organizacionBuscada);
        repositorioPeticion.guardar(peticion);

        response.redirect("/usuario/"+usuario.getId()+"/peticion/success");
        return response;
    }

    public ModelAndView pantallaDePeticionSuccess(Request request, Response response) {
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(request.params("idUsuario")));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario", usuario);
        }},"emisionPeticionSuccess.hbs");
    }


    public String pantallaCrearOrganizacion(Request request, Response response) {
        // TODO: DEVOLVER LA PANTALLA CON LOS CAMPOS PARA EL USUARIO
        return null;
    }

    public Response crearOrganizacion(Request request, Response response) {
//      Organizacion(String razonSocial, TipoOrganizacion tipoOrganizacion, Clasificacion clasificacion, Ubicacion ubicacion)
        String razonSocial = request.params("razonSocial");
        TipoOrganizacion tipoOrganizacion = TipoOrganizacion.valueOf(request.params("tipoOrganizacion"));
        Clasificacion clasificacion = new Clasificacion(request.params("clasificacion"));
        //TODO SEGUIR REGISTRANDO CON UBICACION
        return response;
    }
}
