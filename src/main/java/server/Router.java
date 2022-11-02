package server;

import ServicioGeoRefAPIgob.ServicioGeoRefAPI;
import ServicioGeoRefAPIgob.retrofit.ServicioGeoRefAPIRetrofit;
import controllers.*;
import controllers.userlog.LoginController;
import controllers.userlog.RegisterController;
import controllers.userlog.UsuarioController;
import helpers.PermisoHelper;
import middlewares.AuthMiddleware;
import models.Combustible.Combustible;
import models.MediosDeTransporte.MediosDeTransporte;
import models.Usuarios.FactorDeEmision;
import models.Usuarios.Permiso;
import models.db.EntityManagerHelper;
import models.db.PersistenciaInicial;
import models.domain.services.ServicioGeoDDS;
import models.domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import models.trayecto.Provincia;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioProvincia;
import spark.Route;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

import javax.swing.plaf.synth.SynthUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() throws IOException {
        Router.initEngine();
        // TODO: MANDAR ESTO AL CONFIGURADOR //
        RepositorioProvincia repositorioProvincia = new RepositorioProvincia();
        System.out.println(repositorioProvincia.traerTodas().size()+ "-------------------------------------------------------");
        if(repositorioProvincia.traerTodas().isEmpty()) {
            ServicioGeoRefAPI servicioGeoRefAPI = new ServicioGeoRefAPI();
            servicioGeoRefAPI.setAdapter(new ServicioGeoRefAPIRetrofit());
            List<Provincia> provincias = repositorioProvincia.cargarProvincias();
            repositorioProvincia.persistirProvincias(provincias);
        }
        Spark.staticFileLocation("/public");
        Router.configure();
        PersistenciaInicial.persistirCombustibles();
        ServicioGeoDDS servicioGeoDDS = ServicioGeoDDS.getInstance();
        servicioGeoDDS.setAdapter(new ServicioGeoDDSRetrofitAdapter());
    }

    private static void configure() {
        LoginController loginController = new LoginController();
        OrganizacionController organizacionController= new OrganizacionController();
        ReporteController reporteController = new ReporteController();
        PeticionController peticionController = new PeticionController();
        MiembroController miembroController = new MiembroController();
        RegisterController registerController = new RegisterController();
        UsuarioController usuarioController = new UsuarioController();


        Spark.path("/login", () -> {
            Spark.get("", loginController::pantallaDeLogin, engine);
            Spark.post("", loginController::login);
            Spark.post("/logout", loginController::logout);
        });

        Spark.path("/register", () -> {
            Spark.get("", registerController::getPantallaRegister, engine);
            Spark.post("", registerController::registrarUsuario);
            Spark.get("/sucess", RegisterController::succesRegister, engine);
        });

        Spark.get("/prohibido", loginController::prohibido, engine);

//        Spark.path("/peticion", () ->{
//            Spark.get("", peticionController::pantallaDePeticion, engine);
//            Spark.post("", peticionController::guardar);
//        });

        Spark.path("/usuario/:idUsuario", () -> {
            Spark.get("", usuarioController::pantallaHome, engine);
            Spark.get("/crearOrganizacion",usuarioController::pantallaCrearOrganizacion); // agregar engine
            Spark.post("/crearOrganizacion",usuarioController::crearOrganizacion);
            Spark.get("/organizaciones/",usuarioController::mostrarOrganizaciones);
            Spark.get("/peticion", usuarioController::pantallaDePeticion, engine);
            Spark.get("/peticion/organizacion", usuarioController::pantallaDePeticionSector);
            Spark.get("/peticion/organizacion/:idOrganizacion", usuarioController::pantallaDePeticionSectores,engine);
            Spark.post("/peticion/organizacion/:idOrganizacion",usuarioController::guardarPeticion);
            Spark.get("/peticion/success", usuarioController::pantallaDePeticionSuccess,engine);
        });

        Spark.path("/organizacion/:idOrganizacion", () -> {
//            Spark.before("", AuthMiddleware::verificarSesion);
//            Spark.before("/*", AuthMiddleware::verificarSesion);
//            Spark.before("", (request, response) -> {
//                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_SECTORES)){
//                    response.redirect("/prohibido");
//                    Spark.halt();
//                }
//            });
//            Spark.before("/*", (request, response) -> {
//                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_SECTORES)){
//                    response.redirect("/prohibido");
//                    Spark.halt();
//                }
//            });
            Spark.get("", organizacionController::mostrar,engine);
            Spark.get("/calcularHC",organizacionController::calcularHC);
            Spark.get("/registros",organizacionController::verRegistros);
            Spark.get("/reportes",reporteController::mostrar, engine);
            Spark.get("/peticiones", peticionController::pantallaDePeticiones, engine);
            Spark.post("/peticiones/:idPeticion/aceptar", peticionController::aceptarPeticion);
            Spark.post("/peticiones/:idPeticion/rechazar", peticionController::rechazarPeticion);
        });

        Spark.path("/miembro/:idMiembro", () -> {
//            Spark.before("", AuthMiddleware::verificarSesion);
//            Spark.before("/*", AuthMiddleware::verificarSesion);
            Spark.get("/miembro/:idMiembro/organizacion/:idOrganizacion",miembroController::mostrarDetalleOrganizacion,engine);
            Spark.get("", miembroController::mostrarTrayectos,engine);
            Spark.get("/:idTrayecto/tramos",miembroController::mostrarTramos, engine);
            Spark.get("/registrarTrayecto",miembroController::pantallaDeRegistrarTrayectos, engine);
            Spark.get("/registrarTrayecto/Provincia/:idProvincia", miembroController::pantallaDeRegistrarDireccionInicialTrayecto, engine);
            Spark.post("/registrarTrayecto/Provincia/:idProvincia", miembroController::registrarDireccionInicialTrayecto);
            Spark.get("/registrarTrayecto/DireccionInicial/:idDireccionInicial", miembroController::pantallaDeRegistrarTrayectosConDireccionInicial, engine);
            Spark.get("/registrarTrayecto/DireccionInicial/:idDireccionInicial/ProvinciaFin/:idProvinciaFin",miembroController :: pantallaDeRegistrarDireccionFinalTrayecto, engine);
            Spark.post("/registrarTrayecto/DireccionInicial/:idDireccionInicial/ProvinciaFin/:idProvinciaFin",miembroController :: registrarTrayecto);
//            Spark.post("/registrarTrayecto",miembroController::instanciacionDeTrayecto);
            Spark.get("/registrarTrayecto/:idTrayecto/agregarTramo", miembroController::pantallaDeAgregarTramos, engine);
            Spark.get("/registrarTrayecto/:idTrayecto/agregarTramo/DireccionInicial/Provincia/:idProvinciaInicio", miembroController::pantallaDeRegistrarTramoDireccionInicial, engine);
            Spark.post("/registrarTrayecto/:idTrayecto/agregarTramo/DireccionInicial/Provincia/:idProvinciaInicio", miembroController::registrarTramoDireccionInicial);
            Spark.get("/registrarTrayecto/:idTrayecto/agregarTramo/DireccionInicial/:idDireccionInicial", miembroController::registrarTramoProvinciaFin, engine);
            Spark.get("/registrarTrayecto/:idTrayecto/agregarTramo/DireccionInicial/:idDireccionInicial/ProvinciaFin/:idProvinciaFin", miembroController::pantallaDeRegistrarTramo, engine);
            Spark.post("/registrarTrayecto/:idTrayecto/agregarTramo/DireccionInicial/:idDireccionInicial/ProvinciaFin/:idProvinciaFin", miembroController::registrarTramo);

//            Spark.post("/registrarTrayecto/:idTrayecto", miembroController::registrarTramo);
//            Spark.post("/miembro/:idMiembro/organizacion/:idOrganizacion/registrarTrayecto",miembroController::guardarNuevoTrayecto);
//            Spark.get("/registrarTrayecto/:idTrayecto", miembroController::pantallaDeEditarTrayecto, engine);
        });
    }

}
