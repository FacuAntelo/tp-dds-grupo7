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

import java.util.ArrayList;
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
            put("titulo", "Huella de carbono total por sector territorial");
            put("tipoNombre", "Sector territorial");
        }},"/organizacion/reporte/reporteConNombreYValor.hbs");
    }
    public ModelAndView pantallaHCPorClasificacion(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));

        List<ReporteNombreValor> reportes = GeneradorDeReportes.generarReporteHCTotalPorTipoDeOrganizacion(organizacionBuscado.getClasificacion());

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);
            put("reportes", reportes);
            put("titulo", "Huella de carbono total por clasificación");
            put("tipoNombre", "Clasificacion");
        }},"/organizacion/reporte/reporteConNombreYValor.hbs");
    }
    public ModelAndView pantallacomposicionHCDeSectorTerritorial(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));

        List<ReporteNombreValor> reportes = new ArrayList<>();
        organizacionBuscado.getSectoresTerritoriales().forEach(sectorTerritorial -> {
            reportes.addAll(GeneradorDeReportes.generarReporteComposicionHCTotalDeUnSectorTerritorial(sectorTerritorial));
        });
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);
            put("reportes", reportes);
            put("tipoDatoExtra", "Sector Territorial");
            put("titulo", "Composición huella de carbono total por sector territorial");
            put("tipoNombre", "Organización");
        }},"/organizacion/reporte/reporteConNombreValorYDatoExtra.hbs");
    }

    //TODO
    public ModelAndView pantallacomposicionHCDelPais(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));


        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);

        }},"/organizacion/reporte/reportes.hbs");
    }

    //TODO
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

        List<ReporteNombreValor> reportes = new ArrayList<>();
        organizacionBuscado.getSectoresTerritoriales().forEach(sectorTerritorial -> {
            reportes.addAll(GeneradorDeReportes.generarReporteEvolucionHCTotalDeSectorTerritorial(sectorTerritorial));
        });

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);
            put("reportes", reportes);
            put("tipoDatoExtra", "Sector Territorial");
            put("titulo", "Evolucion huella de carbono total por sector territorial");
            put("tipoNombre", "Fecha");
        }},"/organizacion/reporte/reporteConNombreValorYDatoExtra.hbs");
    }
    public ModelAndView pantallaevolucionHCDeOrganizacion(Request request, Response response){
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));

        List<ReporteNombreValor> reportes = GeneradorDeReportes.generarReporteEvolucionHCTotalDeOrganizacion(organizacionBuscado);

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);
            put("reportes", reportes);
            put("titulo", "Evolucion huella de carbono total por sector territorial");
            put("tipoNombre", "Fecha");
            put("tipoDatoExtra", "Organización");
        }},"/organizacion/reporte/reporteConNombreValorYDatoExtra.hbs");
    }
}
