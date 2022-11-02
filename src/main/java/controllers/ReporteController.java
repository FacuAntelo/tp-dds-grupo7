package controllers;

import models.HuellaDeCarbono.RegistroHC;
import models.Organizacion.Organizacion;
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

    public ModelAndView mostrarPantallaDeOpciones(Request request, Response response){
        //TODO COMPLETAR HBS
        String idOrganización = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganización));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);

        }},"/parts/formReportes.hbs");
    }
}
