package server;

import controllers.*;
import controllers.userlog.LoginController;
import controllers.userlog.RegisterController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

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


        Spark.path("/login", () -> {
            Spark.get("", loginController::pantallaDeLogin, engine);
            Spark.post("", loginController::login);
            Spark.post("/logout", loginController::logout);
        });

        Spark.path("/register", () -> {
            Spark.get("", registerController::getPantallaRegister, engine);
            Spark.post("", registerController::obtenerDatos);
            Spark.get("/sucess", RegisterController::succesRegister, engine);
        });

        Spark.path("/peticion", () ->{
            Spark.get("", peticionController::pantallaDePeticion, engine);
            Spark.post("", peticionController::guardar);
        });

        Spark.path("/organizacion/:idOrganizacion", () -> {
            Spark.get("", organizacionController::mostrar,engine);
            Spark.get("/reportes",reporteController::mostrar, engine);
            Spark.get("/peticiones", peticionController::mostrar, engine);
            Spark.post("/peticiones/:idPeticion/aceptar", peticionController::aceptarPeticion);
            Spark.post("/peticiones/:idPeticion/rechazar", peticionController::rechazarPeticion);
        });

        Spark.path("/miembro/:idMiembro", () -> {
            Spark.get("", miembroController::mostrarTrayectos,engine);
            Spark.get("/:idTrayecto/tramos",miembroController::mostrarTramos, engine);
        });
    }
}
