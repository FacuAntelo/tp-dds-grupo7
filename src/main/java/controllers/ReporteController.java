package controllers;

import models.HuellaDeCarbono.RegistroHC;
import models.Organizacion.Organizacion;
import models.Reportes.GeneradorDeReportes;
import models.Reportes.ReporteNombreValor;
import repositories.RepositorioDA;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioRegistro;
import repositories.RepositorioReportes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class ReporteController {
    RepositorioReportes repositorioReportes = new RepositorioReportes();
    RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();

    public ModelAndView pantallaReportes(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));


        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);

        }},"/organizacion/reporte/reportes.hbs");
    }
    public ModelAndView pantallaHCPorSectorTerritorial(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));

        List<ReporteNombreValor> reportes = GeneradorDeReportes.generarReporteHCTotalPorSectorTerritorial();

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);
            put("reportes", reportes);
        }},"/organizacion/reporte/pantallaHCPorSectorTerritorial.hbs");
    }
    public ModelAndView pantallaHCPorClasificacion(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));


        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);

        }},"/organizacion/reporte/reportes.hbs");
    }
    public ModelAndView pantallacomposicionHCDeSectorTerritorial(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));


        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);

        }},"/organizacion/reporte/reportes.hbs");
    }
    public ModelAndView pantallacomposicionHCDelPais(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));


        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);

        }},"/organizacion/reporte/reportes.hbs");
    }
    public ModelAndView pantallacomposicionHCDeOrganizacion(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));


        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);

        }},"/organizacion/reporte/reportes.hbs");
    }
    public ModelAndView pantallaevolucionHCPorSectorTerritorial(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));


        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);

        }},"/organizacion/reporte/reportes.hbs");
    }
    public ModelAndView pantallaevolucionHCDeOrganizacion(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));


        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);

        }},"/organizacion/reporte/reportes.hbs");
    }
}
