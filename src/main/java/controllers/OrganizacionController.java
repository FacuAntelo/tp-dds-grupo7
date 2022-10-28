package controllers;

import models.Miembro.Miembro;
import models.Organizacion.Organizacion;
import models.Organizacion.Peticion;
import models.Reportes.GeneradorDeReportes;
import models.Sector.Sector;
import repositories.RepositorioMiembro;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioPeticion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class OrganizacionController {

    RepositorioOrganizacion repo = new RepositorioOrganizacion();

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
            put("reportes", GeneradorDeReportes.reporteDeHCdeSectores(organizacionBuscado));
        }},"organizacion/homeOrganizacion.hbs");
    }

}
