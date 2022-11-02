package controllers;

import models.HuellaDeCarbono.CalculadoraHC;
import models.HuellaDeCarbono.RegistroHC;
import models.Miembro.Miembro;
import models.Organizacion.Organizacion;
import models.Organizacion.Peticion;
import models.Reportes.GeneradorDeReportes;
import models.Reportes.ReporteSectoresOrganizacionDTO;
import models.Sector.Sector;
import repositories.RepositorioMiembro;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioPeticion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

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

//        List<ReporteSectoresOrganizacionDTO> reporteSectoresOrganizacionDTOS = GeneradorDeReportes.reporteDeHCdeSectores(organizacionBuscado);

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);

//            if(!reporteSectoresOrganizacionDTOS.isEmpty()) {
//                put("reportes", GeneradorDeReportes.reporteDeHCdeSectores(organizacionBuscado));
//            }

        }},"organizacion/homeOrganizacion.hbs");
    }


    public ModelAndView calcularHC(Request request, Response response) {
        RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
        Organizacion organizacion = repositorioOrganizacion.buscar(Integer.valueOf(request.params("idOrganizacion")));
        organizacion.calcularHC();
        //Se podria redirigir a una pantalla que muestre los datos obtenidos
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacion);
        }},"organizacion/calculadoraHC.hbs");
    }

    public Response verRegistros(Request request, Response response) {
        Organizacion organizacion = new RepositorioOrganizacion().buscar(Integer.valueOf(request.params("idOrganizacion")));
        // TODO organizacion.getRegistrosHC();
        return null;
    }

    public Response ejecutarCalculadoraHC(Request request, Response response) {
        RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
        Organizacion organizacion = repositorioOrganizacion.buscar(Integer.valueOf(request.params("idOrganizacion")));
        RegistroHC registroHC = organizacion.calcularHC();
        repositorioOrganizacion.guardar(organizacion);

        response.redirect("/organizacion/"+organizacion.getId()+"/registros");
        return response;
    }

}
