package server;

import controllers.LoginController;
import controllers.OrganizacionController;
import controllers.PeticionController;
import controllers.ReporteController;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;
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

        Spark.path("/login", () -> {
            Spark.get("", loginController::pantallaDeLogin, engine);
            Spark.post("", loginController::login);
            Spark.post("/logout", loginController::logout);
        });

        Spark.path("/peticion", () ->{
            Spark.get("", peticionController::pantallaDePeticion, engine);
            Spark.post("", peticionController::guardar);
        });

        Spark.path("/organizacion", () -> {
            Spark.get("/:idOrganizacion", organizacionController::mostrar,engine);
            Spark.get("/:idOrganizacion/reportes",reporteController::mostrar, engine);
            Spark.post("/:idOrganizacion/:idPeticion/:idSector", peticionController::aceptarPeticion);
            Spark.get("/:idOrganizacion/peticion", peticionController::mostrar, engine);
        });
    }
}
