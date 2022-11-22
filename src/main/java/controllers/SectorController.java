package controllers;

import models.Organizacion.Organizacion;
import models.Sector.Sector;
import models.db.EntityManagerHelper;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioSector;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import static java.lang.Thread.sleep;


public class SectorController {
    RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
    RepositorioSector repositorioSector = new RepositorioSector();

    public ModelAndView devolverPantallaDeSectores(Request request, Response response){
        int idOrganizacion = Integer.valueOf(request.params("idOrganizacion"));
        Organizacion organizacion = repositorioOrganizacion.buscar(idOrganizacion);
        List<Sector> sectorList = repositorioOrganizacion.buscarTodosLosSectores(idOrganizacion);

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacion);
            put("sectores", sectorList);
        }},"organizacion/sectores.hbs");
    }
    public ModelAndView devolverPantallaAgregarSectores(Request request, Response response){
        int idOrganizacion = Integer.valueOf(request.params("idOrganizacion"));
        Organizacion organizacion = repositorioOrganizacion.buscar(idOrganizacion);
        List<Sector> sectorList = repositorioOrganizacion.buscarTodosLosSectores(idOrganizacion);
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacion);
            put("sectores", sectorList);
        }},"organizacion/agregarSectores.hbs");
    }

    public Response agregarSector(Request request, Response response) throws InterruptedException {
        Sector sector = new Sector();
        sector.setNombre(request.queryParams("sector"));

        int idOrganizacion = Integer.valueOf(request.params("idOrganizacion"));
        Organizacion organizacion = repositorioOrganizacion.buscar(idOrganizacion);
        organizacion.agregarSector(sector);

        repositorioSector.guardar(sector);

        response.redirect("/organizacion/"+organizacion.getId()+"/agregarSector/success");

        return response;
    }

    public ModelAndView pantallaAltaDeSectorSuccess(Request request, Response response){
        int idOrganizacion = Integer.valueOf(request.params("idOrganizacion"));
        Organizacion organizacion = repositorioOrganizacion.buscar(idOrganizacion);
        List<Sector> sectorList = repositorioOrganizacion.buscarTodosLosSectores(idOrganizacion);
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacion);
            put("sectores", sectorList);
        }},"organizacion/altaDeSectorSuccess.hbs");
    }

}
