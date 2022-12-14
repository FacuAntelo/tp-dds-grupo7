package controllers;

import models.Combustible.Combustible;
import models.DTO.ServicioContratadoDTO;
import models.DTO.TramoDTO;
import models.DTO.TrayectoDTO;
import models.MediosDeTransporte.*;
import models.Miembro.Miembro;
import models.Miembro.TipoDocumento;
import models.Organizacion.Organizacion;
import models.Reportes.GeneradorDeReportes;
import models.Sector.Sector;
import models.Usuarios.Usuario;
import models.domain.services.ServicioGeoDDS;
import models.domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import models.trayecto.*;
import repositories.*;
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
    RepositorioCombustible repositorioCombustible= new RepositorioCombustible();
    RepositorioMedioDeTransporte repositorioMedioDeTransporte=new RepositorioMedioDeTransporte();
    RepositorioProvincia repositorioProvincia=new RepositorioProvincia();
    RepositorioLocalidad repositorioLocalidad=new RepositorioLocalidad();
    RepositorioDireccion repositorioDireccion=new RepositorioDireccion();
    RepositorioSector repositorioSector = new RepositorioSector();

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
        List<Provincia> provincias = repositorioProvincia.traerTodas();
        Miembro miembroBuscado = repositorioMiembro.buscar(idMiembro);
        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario", miembroBuscado);
            put("provincias", provincias);
        }},"miembro/registrarTrayecto.hbs");
    }

    public ModelAndView pantallaDeRegistrarDireccionInicialTrayecto(Request request, Response response){
        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        Miembro miembroBuscado = repositorioMiembro.buscar(idMiembro);
        int idProvincia = Integer.parseInt(request.params("idProvincia"));
        Provincia provincia= repositorioProvincia.buscarPorId(idProvincia);

        System.out.println(provincia.getNombre());
//        RepositorioLocalidad repositorioLocalidad = new RepositorioLocalidad();
//        List<Localidad> localidadList = repositorioLocalidad.buscarPorIdProvincia((int) provincia.getId());
        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario", miembroBuscado);
            put("provincia", provincia);
            put("localidades", provincia.getLocalidades());
        }},"miembro/localidad_de_provincia.hbs");
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
                    new Localidad(),
//                    new Provincia(request.queryParams("provinciaInicio"))
                    new Provincia(request.queryParams("Buenos Aires"))
            );
            Direccion direccionFin = new Direccion(request.queryParams("calleFin"),
                    Integer.parseInt(request.queryParams("alturaFin")),
//                    new Localidad(Integer.parseInt(request.queryParams("localidadFin"))),
//                    new Provincia(request.queryParams("provinciaFin"))
                    new Localidad(),
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
        Provincia provinciaInicio = repositorioProvincia.buscarPorId(Integer.parseInt(request.queryParams("provinciaInicio")));
        Provincia provinciaFin = repositorioProvincia.buscarPorId(Integer.parseInt(request.queryParams("provinciaFin")));


//        Direccion direccionInicio = new Direccion(request.queryParams("calleInicio"),
//                Integer.parseInt(request.queryParams("alturaInicio")),
//                    new Localidad(Integer.parseInt(request.queryParams("localidadInicio"))),
//                provinciaInicio)
//
//        );
//        Direccion direccionFin = new Direccion(request.queryParams("calleFin"),
//                Integer.parseInt(request.queryParams("alturaFin")),
//                    new Localidad(Integer.parseInt(request.queryParams("localidadFin"))),
//                provinciaFin)
//        );

//        Trayecto trayecto = new Trayecto(direccionInicio,direccionFin);
//        miembro.agregarTrayecto(trayecto, organizacion);
//
//        repositorioTrayecto.guardar(trayecto);
//        repositorioOrganizacion.guardar(organizacion);
//

//        response.redirect("/miembro/"+ miembro.getId()+"/registrarTrayecto/"+ trayecto.getId());
        
        return response;
    }


    public Response registrarDireccionInicialTrayecto(Request request, Response response) {
        int idProvincia = Integer.parseInt(request.params("idProvincia"));
        Provincia provincia= repositorioProvincia.buscarPorId(idProvincia);
        long idLocalidad = Long.parseLong(request.queryParams("localidadInicio"));
        Localidad localidad = repositorioLocalidad.buscarPorId(idLocalidad);

        Direccion direccionInicio = repositorioDireccion.buscarYGuardar(request.queryParams("calleInicio"),
                Integer.parseInt(request.queryParams("alturaInicio")),
                localidad,
                provincia);

        response.redirect("/miembro/"+request.params("idMiembro")+"/registrarTrayecto/DireccionInicial/"+ direccionInicio.getId());
        return response;
    }

    public ModelAndView pantallaDeRegistrarTrayectosConDireccionInicial(Request request, Response response) {
        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        Miembro miembro = repositorioMiembro.buscar(idMiembro);
        Organizacion organizacion = repositorioMiembro.buscarOrganizacionQuePertenece(miembro);
        Direccion direccionInicial = repositorioDireccion.buscar(Integer.valueOf(request.params("idDireccionInicial")));
        List<Provincia> provincias = repositorioProvincia.traerTodas();

        return new ModelAndView(new HashMap<String, Object>(){{
            put("miembro", miembro);
            put("organizacion", organizacion);
            put("direccionInicial", direccionInicial);
            put("localidadInicio", direccionInicial.getLocalidad());
            put("provinciaInicio", direccionInicial.getProvincia());
            put("provincias", provincias);
        }},"miembro/trayecto/registrarTrayectosConDireccionInicial.hbs");
    }

    public ModelAndView pantallaDeRegistrarDireccionFinalTrayecto(Request request, Response response) {
        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        Miembro miembro = repositorioMiembro.buscar(idMiembro);
        int idProvinciaFin = Integer.parseInt(request.params("idProvinciaFin"));
        Provincia provinciaFin= repositorioProvincia.buscarPorId(idProvinciaFin);
        Direccion direccionInicial = repositorioDireccion.buscar(Integer.valueOf(request.params("idDireccionInicial")));


        return new ModelAndView(new HashMap<String, Object>(){{
            put("miembro", miembro);
            put("direccionInicial", direccionInicial);
            put("localidadInicio", direccionInicial.getLocalidad());
            put("provinciaInicio", direccionInicial.getProvincia());
            put("provinciaFin", provinciaFin);
            put("localidades", provinciaFin.getLocalidades());
        }},"miembro/trayecto/registrarTrayectoDireccionFinal.hbs");

    }

    public Response  registrarTrayecto(Request request, Response response) {
        Miembro miembro = repositorioMiembro.buscar(Integer.parseInt(request.params("idMiembro")));
        Organizacion organizacion = repositorioMiembro.buscarOrganizacionQuePertenece(miembro);
        Direccion direccionInicio = repositorioDireccion.buscar(Integer.valueOf(request.params("idDireccionInicial")));


        Provincia provinciaFin = repositorioProvincia.buscarPorId(Integer.parseInt(request.params("idProvinciaFin")));
        long idLocalidadFin = Long.parseLong(request.queryParams("localidadFin"));
        Localidad localidadFin = repositorioLocalidad.buscarPorId(idLocalidadFin);

        Direccion direccionFin = repositorioDireccion.buscarYGuardar(request.queryParams("calleFin"),
                Integer.parseInt(request.queryParams("alturaFin")),
                localidadFin,
                provinciaFin);


        Trayecto trayecto = new Trayecto(direccionInicio,direccionFin);
        miembro.agregarTrayecto(trayecto, organizacion);
        repositorioOrganizacion.guardar(organizacion);


        response.redirect("/miembro/"+ miembro.getId()+"/registrarTrayecto/"+ trayecto.getId()+"/agregarTramo");
        return response;
    }

    public ModelAndView pantallaDeAgregarTramos(Request request, Response response) {
        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        Miembro miembroBuscado = repositorioMiembro.buscar(idMiembro);
        int idTrayecto = Integer.parseInt(request.params("idTrayecto"));
        Trayecto trayecto = repositorioTrayecto.buscar(idTrayecto);
        List<TramoDTO> tramosDeTrayecto = repositorioMiembro.buscarTramos(miembroBuscado,trayecto.getId());
        List<Provincia> provincias = repositorioProvincia.traerTodas();

//        List<String> tipoTransporteList= Arrays.stream(TipoTransporte.values()).map(x-> x.name()).collect(Collectors.toList());
//        List<String> tipoVehiculoParticular = Arrays.stream(TipoVehiculo.values()).map(x-> x.name()).collect(Collectors.toList());
//        List<Combustible> combustibleList= repositorioCombustible.buscarTodos();
//        List<MediosSinContaminar> mediosSinContaminar= repositorioMedioDeTransporte.obtenerTodosLosMediosSinContaminar();
//        List<ServicioContratadoDTO> servicioContratadoDTOList= repositorioMedioDeTransporte.obtenerTodosLosServiciosContratadosDTO();

        return new ModelAndView(new HashMap<String, Object>(){{
            put("miembro", miembroBuscado);
            put("trayecto", trayecto);
            put("tramosDelTrayecto", tramosDeTrayecto);
            put("provincias", provincias);
//            put("tipos_de_transporte", tipoTransporteList);
//            put("tipo_de_vehiculo", tipoVehiculoParticular);
//            put("tipos_de_combustible", combustibleList);
//            put("mediosSinContaminar", mediosSinContaminar);
//            put("servicios_contratados", servicioContratadoDTOList);
        }},"miembro/tramo/registrarTramoProvinciaInicio.hbs");
    }

    public ModelAndView pantallaDeRegistrarTramoDireccionInicial(Request request, Response response) {
        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        Miembro miembroBuscado = repositorioMiembro.buscar(idMiembro);
        int idTrayecto = Integer.parseInt(request.params("idTrayecto"));
        Trayecto trayecto = repositorioTrayecto.buscar(idTrayecto);
        List<TramoDTO> tramosDeTrayecto = repositorioMiembro.buscarTramos(miembroBuscado,trayecto.getId());
        Provincia provinciaInicio = repositorioProvincia.buscarPorId(Integer.parseInt(request.params("idProvinciaInicio")));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("miembro", miembroBuscado);
            put("trayecto", trayecto);
            put("tramosDelTrayecto", tramosDeTrayecto);
            put("provinciaInicio", provinciaInicio);
            put("localidades", provinciaInicio.getLocalidades());
        }},"miembro/tramo/registrarTramoDireccionInicial.hbs");
    }

    public Response registrarTramoDireccionInicial(Request request, Response response) {
        int idProvincia = Integer.parseInt(request.params("idProvinciaInicio"));
        Provincia provincia= repositorioProvincia.buscarPorId(idProvincia);
        long idLocalidad = Long.parseLong(request.queryParams("localidadInicio"));
        Localidad localidad = repositorioLocalidad.buscarPorId(idLocalidad);
        // href = localidad/?nombre=localidad

        Direccion direccionInicio = repositorioDireccion.buscarYGuardar(request.queryParams("calleInicio"),
                Integer.parseInt(request.queryParams("alturaInicio")),
                localidad,
                provincia);

        response.redirect("/miembro/"+request.params("idMiembro")+"/registrarTrayecto/"+request.params("idTrayecto")+"/agregarTramo/DireccionInicial/"+direccionInicio.getId());
        return response;
    }

    public ModelAndView registrarTramoProvinciaFin(Request request, Response response) {
        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        Miembro miembroBuscado = repositorioMiembro.buscar(idMiembro);
        int idTrayecto = Integer.parseInt(request.params("idTrayecto"));
        Trayecto trayecto = repositorioTrayecto.buscar(idTrayecto);
        List<TramoDTO> tramosDeTrayecto = repositorioMiembro.buscarTramos(miembroBuscado,trayecto.getId());

        Direccion direccionInicial = repositorioDireccion.buscar(Integer.valueOf(request.params("idDireccionInicial")));
        List<Provincia> provincias = repositorioProvincia.traerTodas();

        return new ModelAndView(new HashMap<String, Object>(){{
            put("miembro", miembroBuscado);
            put("trayecto", trayecto);
            put("tramosDelTrayecto", tramosDeTrayecto);
            put("direccionInicial", direccionInicial);
            put("localidadInicio", direccionInicial.getLocalidad());
            put("provinciaInicio", direccionInicial.getProvincia());
            put("provincias", provincias);
        }},"miembro/tramo/registrarTramoProvinciaFin.hbs");


    }

    public ModelAndView pantallaDeRegistrarTramo(Request request, Response response) {
        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        Miembro miembroBuscado = repositorioMiembro.buscar(idMiembro);
        int idTrayecto = Integer.parseInt(request.params("idTrayecto"));
        Trayecto trayecto = repositorioTrayecto.buscar(idTrayecto);
        List<TramoDTO> tramosDeTrayecto = repositorioMiembro.buscarTramos(miembroBuscado,trayecto.getId());

        int idProvinciaFin = Integer.parseInt(request.params("idProvinciaFin"));
        Provincia provinciaFin= repositorioProvincia.buscarPorId(idProvinciaFin);
        Direccion direccionInicial = repositorioDireccion.buscar(Integer.valueOf(request.params("idDireccionInicial")));

        List<String> tipoTransporteList= Arrays.stream(TipoTransporte.values()).map(x-> x.name()).collect(Collectors.toList());
        List<String> tipoVehiculoParticular = Arrays.stream(TipoVehiculo.values()).map(x-> x.name()).collect(Collectors.toList());
        List<Combustible> combustibleList= repositorioCombustible.buscarTodos();
        List<MediosSinContaminar> mediosSinContaminar= repositorioMedioDeTransporte.obtenerTodosLosMediosSinContaminar();
        List<ServicioContratadoDTO> servicioContratadoDTOList= repositorioMedioDeTransporte.obtenerTodosLosServiciosContratadosDTO();
        List<String> tipoTransportePublico= Arrays.stream(TipoTransportePublico.values()).map(x-> x.name()).collect(Collectors.toList());
        List<Linea> lineasSubte = repositorioMedioDeTransporte.obtenerLineaPorTipoTransportePublico(TipoTransportePublico.SUBTE);
        List<Linea> lineasColectivo = repositorioMedioDeTransporte.obtenerLineaPorTipoTransportePublico(TipoTransportePublico.COLECTIVO);
        List<Linea> lineasTren = repositorioMedioDeTransporte.obtenerLineaPorTipoTransportePublico(TipoTransportePublico.TREN);

        return new ModelAndView(new HashMap<String, Object>(){{
            put("miembro", miembroBuscado);
            put("trayecto", trayecto);
            put("tramosDelTrayecto", tramosDeTrayecto);
            put("direccionInicial", direccionInicial);
            put("localidadInicio", direccionInicial.getLocalidad());
            put("provinciaInicio", direccionInicial.getProvincia());
            put("provinciaFin", provinciaFin);
            put("localidades", provinciaFin.getLocalidades());
            put("tipos_de_transporte", tipoTransporteList);
            put("tipo_de_vehiculo", tipoVehiculoParticular);
            put("tipos_de_combustible", combustibleList);
            put("mediosSinContaminar", mediosSinContaminar);
            put("servicios_contratados", servicioContratadoDTOList);
            put("tipo_transporte_publico", tipoTransportePublico);
            put("lineasSubte", lineasSubte);
            put("lineasColectivo", lineasColectivo);
            put("lineasTren", lineasTren);
        }},"miembro/tramo/registrarTramo.hbs");
    }



    public Response registrarTramo(Request request, Response response) throws IOException {
        int idMiembro = Integer.parseInt(request.params("idMiembro"));
        int idTrayecto = Integer.parseInt(request.params("idTrayecto"));
        Miembro miembro = repositorioMiembro.buscar(idMiembro);
        Trayecto trayecto = repositorioTrayecto.buscar(idTrayecto);
        TipoTransporte tipoTransporte = Enum.valueOf(TipoTransporte.class,request.queryParams("tipos_de_transporte"));

        Boolean esCompartido = Boolean.parseBoolean(request.queryParams("es_compartido"));

        long idLocalidadFin = Long.parseLong(request.queryParams("localidadFin"));
        Localidad localidadFin = repositorioLocalidad.buscarPorId(idLocalidadFin);
        int idProvinciaFin = Integer.parseInt(request.params("idProvinciaFin"));
        Provincia provinciaFin= repositorioProvincia.buscarPorId(idProvinciaFin);


        LocalTime hora=LocalTime.of(Integer.parseInt(request.queryParams("hora")), Integer.parseInt(request.queryParams("minuto")));
        Direccion direccionInicio = repositorioDireccion.buscar(Integer.valueOf(request.params("idDireccionInicial")));
        Direccion direccionFin = repositorioDireccion.buscarYGuardar(request.queryParams("calleFin"),
                Integer.parseInt(request.queryParams("alturaFin")),
                localidadFin,
                provinciaFin );

        Tramo tramo = new Tramo(direccionInicio,direccionFin,hora);

        if(tipoTransporte== TipoTransporte.VEHICULO_PARTICULAR){
            TipoVehiculo tipoVehiculo = Enum.valueOf(TipoVehiculo.class,request.queryParams("tipo_de_vehiculo"));
            Combustible combustible = repositorioCombustible.buscarPorId(Integer.parseInt(request.queryParams("tipo_de_combustible_vehiculo_particular")));

            VehiculoParticular vehiculoParticular = repositorioMedioDeTransporte.obtenerMedioDeTransporte(tipoVehiculo,combustible,esCompartido);

            tramo.setMedioDeTransporte(vehiculoParticular);
        }
        else if (tipoTransporte== TipoTransporte.MEDIOS_SIN_CONTAMINAR){
            MediosSinContaminar medioSinContaminar;

            if(request.queryParams("tipo_de_medio_sin_contaminar").equals("-1")){
                medioSinContaminar = new MediosSinContaminar(request.queryParams("otro_medio_sin_contaminar"),esCompartido);
                repositorioMedioDeTransporte.guardar(medioSinContaminar);
            }
            else {
                medioSinContaminar = repositorioMedioDeTransporte.obtenerMedioDeTransporte(request.queryParams("tipo_de_medio_sin_contaminar"), esCompartido);
            }

            tramo.setMedioDeTransporte(medioSinContaminar);

        }
        else if (tipoTransporte== TipoTransporte.SERVICIO_CONTRATADO){

            ServicioContratado servicio = (ServicioContratado) repositorioMedioDeTransporte.buscar(Integer.parseInt(request.queryParams("servicio_contratado")));
            Combustible combustible = repositorioCombustible.buscarPorId(Integer.parseInt(request.queryParams("tipo_de_combustible_servicio_contratado")));
            ServicioContratado servicioContratado = repositorioMedioDeTransporte.obtenerMedioDeTransporte(servicio.getServicio(),combustible,esCompartido);

            tramo.setMedioDeTransporte(servicioContratado);

        }
        trayecto.agregarTramo(tramo);
        repositorioTrayecto.guardar(trayecto);

        response.redirect("/miembro/"+ miembro.getId()+"/registrarTrayecto/"+ trayecto.getId()+"/agregarTramo");

        return response;
    }

    public ModelAndView pantallaMiembrosDelSector(Request request, Response response){
        int idOrganizacion = Integer.valueOf(request.params("idOrganizacion"));
        Organizacion organizacion = repositorioOrganizacion.buscar(idOrganizacion);

        Integer idSector = Integer.valueOf(request.params("idSector"));
        Sector sector = repositorioSector.buscar(idSector);
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacion);
            put("sector", sector);
            put("miembros", sector.getMiembros());
        }},"organizacion/miembrosDelSector.hbs");
    }
}
