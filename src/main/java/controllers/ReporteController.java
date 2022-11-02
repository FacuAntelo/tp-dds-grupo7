package controllers;

import models.HuellaDeCarbono.RegistroHC;
import repositories.RepositorioDA;
import repositories.RepositorioRegistro;
import repositories.RepositorioReportes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;

public class ReporteController {
    RepositorioReportes repositorioReportes = new RepositorioReportes();

    public ModelAndView mostrarPantallaDeOpciones(Request request, Response response){
        //TODO COMPLETAR HBS
        return new ModelAndView(null, "/parts/formReportes.hbs");
    }
}
