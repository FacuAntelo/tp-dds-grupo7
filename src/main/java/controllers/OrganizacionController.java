package controllers;

import models.Organizacion.Organizacion;
import models.Reportes.GeneradorDeReportes;
import repositories.RepositorioOrganizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class OrganizacionController {

    public static String traerOrganizacion(Request request, Response response) {
        RepositorioOrganizacion repo = new RepositorioOrganizacion();
        if(repo == null){
            return "no existe organizacion";
        }
        return repo.buscar(Integer.parseInt(request.params("id"))).getRazonSocial();
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
//            put("sectores", organizacionBuscado.getSectores()); //Prueba para el each en hbs
            put("reportes", GeneradorDeReportes.reporteDeHCdeSectores(organizacionBuscado));
        }},"dashboard.hbs");
    }

}
