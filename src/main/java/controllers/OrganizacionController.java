package controllers;

import models.HuellaDeCarbono.CalculadoraHC;
import models.HuellaDeCarbono.RegistroHC;
import models.MediosDeTransporte.TipoTransporte;
import models.Miembro.Miembro;
import models.Organizacion.*;
import models.Reportes.GeneradorDeReportes;
import models.Reportes.ReporteSectoresOrganizacionDTO;
import models.Sector.Sector;
import models.Usuarios.Usuario;
import models.trayecto.Direccion;
import models.trayecto.Localidad;
import models.trayecto.Provincia;
import repositories.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizacionController {

    RepositorioOrganizacion repo = new RepositorioOrganizacion();
    RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    RepositorioProvincia repositorioProvincia = new RepositorioProvincia();
    RepositorioLocalidad repositorioLocalidad = new RepositorioLocalidad();
    RepositorioClasificacion repositorioClasificacion = new RepositorioClasificacion();
    RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();

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

    public ModelAndView devolverFormParaDarDeAltaProvinciaOrganizacion(Request request, Response response){
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));
        List<Provincia> provincias = repositorioProvincia.traerTodas();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario", usuario);
            put("provincias", provincias);
        }},"organizacion/crearOrganizacion.hbs");
    }

    public ModelAndView pantallaCrearOrganizacionConProvinciaElegida(Request request, Response response){
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));

        int idProvincia = Integer.parseInt(request.params("idProvincia"));
        Provincia provincia= repositorioProvincia.buscarPorId(idProvincia);

        List<String> tiposOrganizaciones= Arrays.stream(TipoOrganizacion.values()).map(x-> x.name()).collect(Collectors.toList());

        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario", usuario);
            put("provincia", provincia);
            put("localidades", provincia.getLocalidades());
            put("tiposOrganizaciones", tiposOrganizaciones);
        }},"organizacion/crearOrganizacionConProvinciaElegida.hbs");
    }

    public Response darDeAltaOrganizacion(Request request, Response response){

        Organizacion organizacion = new Organizacion();
        Clasificacion clasificacion = new Clasificacion(request.queryParams("clasificacion"));
//        Usuario usuario = repositorioUsuario.find(Integer.parseInt(request.session().attribute("id")));
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(request.params("idUsuario")));

        organizacion.setUsuario(usuario);
        organizacion.setClasificacion(clasificacion);
        organizacion.setRazonSocial(request.queryParams("razonSocial"));
        TipoOrganizacion tipoOrganizacion = Enum.valueOf(TipoOrganizacion.class,request.queryParams("tipoOrganizacion"));
        organizacion.setTipoOrganizacion(tipoOrganizacion);

        Ubicacion ubicacion = new Ubicacion();

        Direccion direccion = new Direccion();

        direccion.setAltura(Integer.parseInt(request.queryParams("altura")));
        direccion.setCalle(request.queryParams("calle"));

        Provincia provincia = repositorioProvincia.buscarPorId(Integer.parseInt(request.params("idProvincia")));
        Localidad localidad = repositorioLocalidad.buscarPorId(Long.parseLong(request.queryParams("localidad")));

        direccion.setLocalidad(localidad);
        direccion.setProvincia(provincia);

        ubicacion.setDireccion(direccion);
        ubicacion.setCodigoPostal(Integer.valueOf(request.queryParams("codigoPostal")));

        organizacion.setUbicacion(ubicacion);

        organizacion.setLocalidad(localidad);
        organizacion.setProvincia(provincia);

        repositorioOrganizacion.guardar(organizacion);

        response.redirect("/organizacion/"+organizacion.getId());

        return  response;
    }

    public ModelAndView pantallaCrearOrganizacionAgregarSector(Request request, Response response){
        String idUsuario = request.params("idUsuario");
        Usuario usuario = repositorioUsuario.find(Integer.parseInt(idUsuario));

        int idProvincia = Integer.parseInt(request.queryParams("provincia"));
        Provincia provincia= repositorioProvincia.buscarPorId(idProvincia);
        long idLocalidadFin = Long.parseLong(request.queryParams("localidad"));
        Localidad localidad = repositorioLocalidad.buscarPorId(idLocalidadFin);
        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario", usuario);
            put("provincia", provincia);
            put("localidad", localidad);
            put("calle", request.queryParams("calle"));
            put("altura", request.queryParams("altura"));
        }},"organizacion/agregarSectores.hbs");
    }

}
