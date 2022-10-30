package server;

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
import repositories.RepositorioOrganizacion;
import spark.Route;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

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

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
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
            Spark.post("/registrarTrayecto",miembroController::instanciacionDeTrayecto);
            Spark.get("/registrarTrayecto/:idTrayecto", miembroController::pantallaDeAgregarTramos, engine);
//            Spark.post("/miembro/:idMiembro/organizacion/:idOrganizacion/registrarTrayecto",miembroController::guardarNuevoTrayecto);
//            Spark.get("/registrarTrayecto/:idTrayecto", miembroController::pantallaDeEditarTrayecto, engine);
        });
    }

}
