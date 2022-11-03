package controllers;

import models.DTO.DatosDeActividadDTO;
import models.Organizacion.DatosDeActividad;
import models.Usuarios.FactorDeEmision;
import models.Usuarios.Usuario;
import repositories.RepositorioDA;
import repositories.RepositorioFactoresDeEmision;
import repositories.RepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdministradorController {
    RepositorioDA repositorioDA = new RepositorioDA();
    RepositorioFactoresDeEmision repositorioFactoresDeEmision= new RepositorioFactoresDeEmision();
    RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

    public ModelAndView homeAdministrador(Request request, Response response) {
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario",usuario);
        }},"administrador/homeAdministrador.hbs");
    }

    public ModelAndView pantallaDeFactoresDeEmision(Request request, Response response) {
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));

        List<FactorDeEmision> factorDeEmisionList= repositorioFactoresDeEmision.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("factoresDeEmision", factorDeEmisionList);
            put("usuario",usuario);
        }},"administrador/factoresDeEmision.hbs");
    }

    public ModelAndView pantallaEditarFactorDeEmision(Request request, Response response) {
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));
        FactorDeEmision factorDeEmisionAEditar = repositorioFactoresDeEmision.buscar(Integer.valueOf(request.params("idFactorEmision")));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("factorDeEmision", factorDeEmisionAEditar);
            put("usuario",usuario);
        }},"administrador/edicionFactorDeEmision.hbs");

    }

    public Response modificarFactorDeEmision(Request request, Response response){
        FactorDeEmision factorDeEmisionAEditar = repositorioFactoresDeEmision.buscar(Integer.valueOf(request.params("idFactorEmision")));
        double valorNuevoFactorEmision = Double.valueOf(request.queryParams("nuevoValorFE"));
        factorDeEmisionAEditar.setValorFactorEmision(valorNuevoFactorEmision);
        factorDeEmisionAEditar.setUnidad(request.queryParams("nuevaUnidadFE"));
        repositorioFactoresDeEmision.guardar(factorDeEmisionAEditar);


        response.redirect("/administrador/"+request.params("idUsuario")+"/factoresDeEmision");
        return response;

    }
}
