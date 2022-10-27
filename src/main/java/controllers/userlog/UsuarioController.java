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

        //BUSCO LAS ORGANIZACIONES QUE ES MIEMBRO EL USUARIO

        List<Organizacion> organizacionList = repositorioOrganizacion.buscarOrganizacionesDeUsuario(Integer.valueOf(idUsuario));

        if(organizacionList.isEmpty()){
            return new ModelAndView(null,"homeUsuario.hbs");
        }
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizaciones", organizacionList);
            put("nombre", usuario);
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
        }},"dashboard.hbs");
    }


    public String mostrarOrganizaciones(Request request, Response response) {
        List<Organizacion> organizacionList = repositorioOrganizacion.buscarOrganizacionesDeUsuario(Integer.valueOf(request.queryParams("idUsuario")));
        return null;
    }

    public ModelAndView pantallaDePeticion(Request request, Response response) {
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));
        List<Organizacion> organizaciones = repositorioOrganizacion.buscarTodos();
        List<Sector> sectores = EntityManagerHelper.getEntityManager().createQuery("from Sector").getResultList();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizaciones", organizaciones);
            put("usuario", usuario);
            put("sectores", sectores);
//            put("sectores", organizacionBuscado.getSectores());
        }},"emisionPeticion.hbs");
    }

    public ModelAndView pantallaDePeticionSiguiente(Request request, Response response) {
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));

        Organizacion organizacion = repositorioOrganizacion.buscar(Integer.parseInt(request.params("idOrganizacion")));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacion);
            put("usuario", usuario);
            put("sectores", organizacion.getSectores());
        }},"emisionPeticionSector.hbs");
    }



}
