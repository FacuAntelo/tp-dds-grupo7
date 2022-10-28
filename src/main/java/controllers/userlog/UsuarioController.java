package controllers.userlog;

import controllers.ReporteController;
import models.Organizacion.Organizacion;
import models.Reportes.GeneradorDeReportes;
import models.Sector.Sector;
import models.Usuarios.Usuario;
import models.db.EntityManagerHelper;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class UsuarioController {
    RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();

    public ModelAndView pantallaHome(Request request, Response response) {
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));

        List<Organizacion> organizacionList = repositorioOrganizacion.buscarOrganizacionesDeUsuario(Integer.valueOf(idUsuario));

        if(organizacionList.isEmpty()){
            return new ModelAndView(null,"homeUsuario.hbs");
        }
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

//    public ModelAndView pantallaDePeticionSector(Request request, Response response) {
//        String idUsuario = request.params("idUsuario");
//        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));
//
//        Organizacion organizacion = repositorioOrganizacion.buscar(Integer.parseInt(request.queryParams("idOrganizacion")));
//
//        return new ModelAndView(new HashMap<String, Object>(){{
//            put("organizacion", organizacion);
//            put("usuario", usuario);
//            put("sectores", organizacion.getSectores());
//        }},"emisionPeticionSector.hbs");
//    }

    public Response pantallaDePeticionSector(Request r, Response res){
        System.out.println(r.queryParams("idOrganizacion") + "----------------------------------------------");
        res.redirect("/usuario/" + r.params("idUsuario") + "/peticion/organizacion/"+ r.queryParams("idOrganizacion"));
        return res;
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
}
