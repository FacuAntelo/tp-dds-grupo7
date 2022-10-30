package controllers;

import models.DTO.TramoDTO;
import models.DTO.TrayectoDTO;
import models.MediosDeTransporte.MediosSinContaminar;
import models.MediosDeTransporte.TipoTransporte;
import models.Miembro.Miembro;
import models.Miembro.TipoDocumento;
import models.Organizacion.Organizacion;
import models.Reportes.GeneradorDeReportes;
import models.Usuarios.Usuario;
import models.trayecto.*;
import repositories.RepositorioMiembro;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioTrayecto;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MiembroController {
    RepositorioMiembro repositorioMiembro = new RepositorioMiembro();
    RepositorioTrayecto repositorioTrayecto= new RepositorioTrayecto();
    RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();

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

    public ModelAndView mostrarDetalleOrganizacion(Request request, Response response){
        Miembro miembro = repositorioMiembro.buscar(Integer.valueOf(request.params("idMiembro")));
        Organizacion organizacion = repositorioOrganizacion.buscar(Integer.valueOf(request.params("idOrganizacion")));

        return null;
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

    public ModelAndView pantallaDeRegistrarTrayectos (Request request, Response response){
        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        Miembro miembroBuscado = repositorioMiembro.buscar(idMiembro);
        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario", miembroBuscado);
        }},"miembro/registrarTrayecto.hbs");
    }

    //PARA MEDIO DE TRANSPORTE: MODIFICAR PARA QUE SEA EL SELECCIONADO
    //PARA LOCALIDAD Y PROVINCIA VER LA API DE LA DISTANCIA Y MODIFICAR
    public Response guardarNuevoTrayecto(Request request, Response response) throws IOException {
        //MODIFICAR PARA QUE SEA DEL SERVICIO SELECCIONADO
        MediosSinContaminar pie = new MediosSinContaminar();

        Trayecto trayecto = new Trayecto();


            LocalTime hora=LocalTime.of(Integer.parseInt(request.queryParams("hora")), Integer.parseInt(request.queryParams("minuto")));
//            tramo.setMedioDeTransporte();
            Direccion direccionInicio = new Direccion(request.queryParams("calleInicio"),
                    Integer.parseInt(request.queryParams("alturaInicio")),
//                    new Localidad(Integer.parseInt(request.queryParams("localidadInicio"))),
                    new Localidad(180),
//                    new Provincia(request.queryParams("provinciaInicio"))
                    new Provincia(request.queryParams("Buenos Aires"))
            );
            Direccion direccionFin = new Direccion(request.queryParams("calleFin"),
                    Integer.parseInt(request.queryParams("alturaFin")),
//                    new Localidad(Integer.parseInt(request.queryParams("localidadFin"))),
//                    new Provincia(request.queryParams("provinciaFin"))
                    new Localidad(180),
                    new Provincia(request.queryParams("Buenos Aires"))
            );
            Tramo tramo = new Tramo(direccionInicio,direccionFin,hora);
            tramo.setMedioDeTransporte(pie);
            trayecto.agregarTramo(tramo);
            repositorioTrayecto.guardar(trayecto);

            response.redirect("/registrarTrayecto" + trayecto.getId());
            return response;

    }

        //TODO:SE DEBE CAMBIAR PARA QUE BUSQUE TODO POR IDUSUARIO
    public ModelAndView pantallaDeEditarTrayecto (Request request, Response response){
        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        int idTrayecto = Integer.parseInt(request.params("idTrayecto"));

        Miembro miembroBuscado = repositorioMiembro.buscar(idMiembro);
        List<TramoDTO> tramoDTOList = repositorioMiembro.buscarTramos(miembroBuscado,idTrayecto);
        Organizacion organizacion = repositorioMiembro.buscarOrganizacionQuePertenece(miembroBuscado);

        Trayecto trayecto = repositorioTrayecto.buscar(idTrayecto);
        return new ModelAndView(new HashMap<String, Object>(){{
            put("trayecto", trayecto);
            put("organizacion", organizacion);
            put("tramos",tramoDTOList);
        }},"miembro/registrarTrayecto.hbs");
    }


    public Response instanciacionDeTrayecto(Request request, Response response) {
        Miembro miembro = repositorioMiembro.buscar(Integer.parseInt(request.params("idMiembro")));
        Organizacion organizacion = repositorioMiembro.buscarOrganizacionQuePertenece(miembro);


        Direccion direccionInicio = new Direccion(request.queryParams("calleInicio"),
                Integer.parseInt(request.queryParams("alturaInicio")),
                    new Localidad(Integer.parseInt(request.queryParams("localidadInicio"))),
                    new Provincia(request.queryParams("provinciaInicio"))

        );
        Direccion direccionFin = new Direccion(request.queryParams("calleFin"),
                Integer.parseInt(request.queryParams("alturaFin")),
                    new Localidad(Integer.parseInt(request.queryParams("localidadFin"))),
                    new Provincia(request.queryParams("provinciaFin"))
        );

        Trayecto trayecto = new Trayecto(direccionInicio,direccionFin);
        miembro.agregarTrayecto(trayecto, organizacion);

        repositorioTrayecto.guardar(trayecto);
        repositorioOrganizacion.guardar(organizacion);


        response.redirect("/miembro/"+ miembro.getId()+"/registrarTrayecto/"+ trayecto.getId());
        
        return response;
    }

    public ModelAndView pantallaDeAgregarTramos(Request request, Response response) {
        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        int idTrayecto = Integer.parseInt(request.params("idTrayecto"));
        Miembro miembroBuscado = repositorioMiembro.buscar(idMiembro);
        Trayecto trayecto = repositorioTrayecto.buscar(idTrayecto);
        List<String> tipoTransporteList= Arrays.stream(TipoTransporte.values()).map(x-> x.name()).collect(Collectors.toList());

        return new ModelAndView(new HashMap<String, Object>(){{
            put("miembro", miembroBuscado);
            put("trayecto", trayecto);
            put("tiposDeTransporte", tipoTransporteList);
        }},"miembro/registrarTramo.hbs");
    }
}
