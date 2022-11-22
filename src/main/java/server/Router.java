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
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

import javax.swing.plaf.synth.SynthUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static spark.debug.DebugScreen.enableDebugScreen;

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

        DebugScreen.enableDebugScreen();

        PersistenciaInicial.persistirTodoLoNecesario();

        Spark.staticFileLocation("/public");
        Spark.staticFiles.externalLocation("upload");
        Router.configure();
    }

    private static void configure() {
        LoginController loginController = new LoginController();
        OrganizacionController organizacionController= new OrganizacionController();
        ReporteController reporteController = new ReporteController();
        PeticionController peticionController = new PeticionController();
        MiembroController miembroController = new MiembroController();
        RegisterController registerController = new RegisterController();
        UsuarioController usuarioController = new UsuarioController();
        RegistroController registroController = new RegistroController();
        AdministradorController administradorController = new AdministradorController();
        SectorController sectorController = new SectorController();


        Spark.path("/login", () -> {
            Spark.before("", (request, response) -> {
                request.session().invalidate();
            });

            Spark.get("", loginController::pantallaDeLogin, engine);
            Spark.post("", loginController::login);
            Spark.get("/logout", loginController::logout);
            Spark.post("/logout", loginController::logout);
        });
        
        Spark.path("",()->{
            Spark.get("/panel",administradorController::homeAdministrador,engine);
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
//            Spark.before("", AuthMiddleware::verificarSesion);
//            Spark.before("/*", AuthMiddleware::verificarSesion);
//            Spark.before("/crearOrganizacion", (request, response) -> {
//                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.CREAR_ORGANIZACIONES)){
//                    response.redirect("/prohibido");
//                    Spark.halt();
//                }
//            });

            Spark.get("", usuarioController::pantallaHomeUsuario, engine);
            Spark.get("/crearOrganizacion",organizacionController::devolverFormParaDarDeAltaProvinciaOrganizacion, engine);
            Spark.post("/crearOrganizacion",organizacionController::darDeAltaProvinciaOrganizacion);
            Spark.get("/crearOrganizacion/Direccion/Provincia/:idProvincia",organizacionController::pantallaCrearOrganizacionConProvinciaElegida, engine);
            Spark.post("/crearOrganizacion/Direccion/Provincia/:idProvincia",organizacionController::darDeAltaOrganizacion);
            Spark.get("/crearOrganizacion/success",organizacionController::pantallaAltaDeOrganizacionSuccess, engine);
//            Spark.get("/crearOrganizacion/agregarSectores",organizacionController::pantallaCrearOrganizacionAgregarSector, engine );
            Spark.get("/elegirOrganizacion", usuarioController:: pantallaElegirOrganizacion, engine);
            Spark.post("/elegirOrganizacion", usuarioController:: redirigirPantalla);
            Spark.get("/peticion", usuarioController::pantallaDePeticion, engine);
            Spark.get("/peticion/organizacion", usuarioController::pantallaDePeticionSector);
            Spark.get("/peticion/organizacion/:idOrganizacion", usuarioController::pantallaDePeticionSectores,engine);
            Spark.post("/peticion/organizacion/:idOrganizacion",usuarioController::guardarPeticion);
            Spark.get("/peticion/success", usuarioController::pantallaDePeticionSuccess,engine);
            Spark.post("/logout", loginController::logout);

        });

        Spark.path("/organizacion/:idOrganizacion", () -> {
//            Spark.before("", AuthMiddleware::verificarSesion);
//            Spark.before("/*", AuthMiddleware::verificarSesion);

            Spark.before("", (request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_ORGANIZACIONES)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
                AuthMiddleware.verificarOrganizacion(request, response);
            });
            Spark.before("/*", (request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_ORGANIZACIONES)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
                AuthMiddleware.verificarOrganizacion(request, response);
            });
            Spark.before("/calcularHC", (request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.CALCULAR_HUEYA_CARBONO)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            });
            Spark.before("/reportes", (request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_REPORTES)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            });
            Spark.before("/registrarMediciones", (request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.REGISTRAR_MEDICIONES)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            });
            Spark.before("/peticiones", (request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_PETICIONES)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            });

            Spark.before("/peticiones/:idPeticion/aceptar", (request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.ACEPTAR_PETICIONES)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            });
            Spark.before("/peticiones/:idPeticion/rechazar", (request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.RECHAZAR_PETICIONES)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            });

            Spark.get("", organizacionController::mostrar,engine);
            Spark.get("/calcularHC",organizacionController::calcularHC,engine);
            Spark.post("/calcularHC", organizacionController:: ejecutarCalculadoraHC);
            Spark.get("/registros",registroController::mostrar, engine);
            Spark.get("/peticiones", peticionController::pantallaDePeticiones, engine);
            Spark.get("/reportes", reporteController::mostrarPantallaDeOpciones, engine);
            Spark.post("/peticiones/:idPeticion/aceptar", peticionController::aceptarPeticion);
            Spark.post("/peticiones/:idPeticion/rechazar", peticionController::rechazarPeticion);
            Spark.get("/registrarMediciones", ExcelController::pantallaCargaExcel,engine);
            Spark.post("/registrarMediciones", ExcelController::cargar);
            Spark.get("/todoOk", ExcelController::todoOk );
            Spark.get("/sectores",sectorController::devolverPantallaDeSectores, engine);
            Spark.get("/agregarSector", sectorController::devolverPantallaAgregarSectores, engine);
            Spark.post("/agregarSector", sectorController::agregarSector);
            Spark.get("/agregarSector/success",sectorController::pantallaAltaDeSectorSuccess, engine);
            Spark.post("/logout", loginController::logout);

        });

        Spark.path("/miembro/:idMiembro", () -> {
//            Spark.before("", AuthMiddleware::verificarSesion);
//            Spark.before("/*", AuthMiddleware::verificarSesion);
            Spark.before("", AuthMiddleware::verificarMiembro);

            Spark.before("/registrarTrayecto", (request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.CREAR_TRAYECTOS)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            });

            Spark.before("/registrarTrayecto/*", (request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.CREAR_TRAYECTOS)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            });
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
            Spark.post("/logout", loginController::logout);
        });

        Spark.path("/administrador/:idUsuario", () ->{
            Spark.get("",administradorController::homeAdministrador,engine);
            Spark.get("/factoresDeEmision", administradorController:: pantallaDeFactoresDeEmision, engine);
            Spark.get("/factoresDeEmision/:idFactorEmision",administradorController::pantallaEditarFactorDeEmision, engine);
            Spark.post("/factoresDeEmision/:idFactorEmision",administradorController::modificarFactorDeEmision);
            Spark.post("/logout", loginController::logout);

        });
    }

}
