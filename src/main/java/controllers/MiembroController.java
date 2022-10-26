package controllers;

import models.DTO.TramoDTO;
import models.DTO.TrayectoDTO;
import models.Miembro.Miembro;
import models.Organizacion.Organizacion;
import models.Reportes.GeneradorDeReportes;
import models.trayecto.Trayecto;
import repositories.RepositorioMiembro;
import repositories.RepositorioOrganizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MiembroController {
    RepositorioMiembro repositorioMiembro = new RepositorioMiembro();

    public ModelAndView mostrarTrayectos (Request request, Response response){

        String idMiembro = request.params("idMiembro");
        Miembro miembroBuscado = repositorioMiembro.buscar(Integer.parseInt(idMiembro));
        List<TrayectoDTO> trayectoDTOList = repositorioMiembro.buscarTrayectos(miembroBuscado);
        Organizacion organizacion = repositorioMiembro.buscarOrganizacionQuePertenece(miembroBuscado);

        if(miembroBuscado==null){
            response.redirect("/login");
        }
        return new ModelAndView(new HashMap<String, Object>(){{
            put("nombre", miembroBuscado);
            put("organizacion", organizacion);
            put("trayectos",trayectoDTOList);
        }},"miembro/trayectos.hbs");
    }

    public ModelAndView mostrarTramos (Request request, Response response){

        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        int idTrayecto = Integer.parseInt(request.params("idTrayecto"));

        Miembro miembroBuscado = repositorioMiembro.buscar(idMiembro);
        List<TramoDTO> tramoDTOList = repositorioMiembro.buscarTramos(miembroBuscado,idTrayecto);
        Organizacion organizacion = repositorioMiembro.buscarOrganizacionQuePertenece(miembroBuscado);

        if(miembroBuscado==null){
            response.redirect("/login");
        }
        return new ModelAndView(new HashMap<String, Object>(){{
            put("nombre", miembroBuscado);
            put("organizacion", organizacion);
            put("tramos",tramoDTOList);
        }},"miembro/tramos.hbs");
    }

}
