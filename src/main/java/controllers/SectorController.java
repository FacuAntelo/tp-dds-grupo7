package controllers;

import models.Organizacion.Organizacion;
import models.Sector.Sector;
import models.db.EntityManagerHelper;
import repositories.RepositorioOrganizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;



public class SectorController {
    RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
//    esto va en el repositorio
//    public void guardar(Sector sector){
//        EntityManagerHelper.getEntityManager().persist(sector);
//    }
    public ModelAndView devolverPantallaDeSectores(Request request, Response response){
        // todo devolver el form para agregar sector
        return null;
    }
    public Response agregarSector(Request request, Response response) {
        Sector sector = new Sector();
        sector.setNombre(request.queryParams("nombre"));
        Organizacion organizacion = repositorioOrganizacion.buscar(Integer.valueOf(request.queryParams("idOrganizacion")));
        organizacion.agregarSector(sector);
        return response;
    }
}
