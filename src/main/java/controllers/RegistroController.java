package controllers;

import models.DTO.RegistroDTO;
import models.HuellaDeCarbono.RegistroHC;
import repositories.RepositorioRegistro;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class RegistroController {
    RepositorioRegistro repositorioRegistro=new RepositorioRegistro();

    public ModelAndView mostrar(Request request, Response response){
        int idOrganizacion= Integer.parseInt(request.params("idOrganizacion"));
        List<RegistroDTO> registroHCList = repositorioRegistro.getRegistroHCDTOListDeOrganizacion(idOrganizacion);
        return new ModelAndView(new HashMap<String, Object>(){{
            put("registroHCList", registroHCList);
        }},"/parts/formReportes.hbs");
    }
}
