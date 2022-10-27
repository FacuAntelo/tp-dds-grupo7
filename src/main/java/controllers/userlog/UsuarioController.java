package controllers.userlog;

import controllers.ReporteController;
import models.Organizacion.Organizacion;
import models.Reportes.GeneradorDeReportes;
import models.Usuarios.Usuario;
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

        List<Organizacion> organizacionList = repositorioOrganizacion.buscarTodos();

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




}
