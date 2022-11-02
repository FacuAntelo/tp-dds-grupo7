package controllers;

import models.HuellaDeCarbono.RegistroHC;
import repositories.RepositorioRegistro;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;

public class ReporteController {
    RepositorioRegistro repositorioRegistro=new RepositorioRegistro();

    public ModelAndView mostrar(Request request, Response response){
        int idOrganizacion= Integer.parseInt(request.params("idOrganizacion"));
        List<RegistroHC> registroHCList = repositorioRegistro.getRegistroHCListDeOrganizacion(idOrganizacion);
        //TODO COMPLETAR HBS
        return new ModelAndView(null, "/parts/formReportes.hbs");
    }
}
