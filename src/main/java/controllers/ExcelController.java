package controllers;

import javafx.animation.PathTransition;
import models.CargaExcel.ExcelUtils;
import models.Organizacion.DatosDeActividad;
import models.Organizacion.Organizacion;
import models.Usuarios.FactorDeEmision;
import models.unidad.Unidad;
import org.apache.commons.io.FileUtils;
import repositories.RepositorioDA;
import repositories.RepositorioOrganizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.mail.MessagingException;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExcelController extends Thread {
    static RepositorioDA repoDA;
   private static PathTransition file;


    public static  Response cargar(Request request, Response response) throws ServletException, IOException, MessagingException {
        //
        String path = "upload/archivoCargado.xltx";

            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            InputStream is = request.raw().getPart("archivoParaSubir").getInputStream();
                // Use the input stream to create a file
            File archivoASubir = new File(path);

        FileUtils.copyInputStreamToFile(is, archivoASubir);


        RepositorioOrganizacion repositorioOrganizacion= new RepositorioOrganizacion();
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(request.params("idOrganizacion")));
        organizacionBuscado.leerExcel(path);
        repositorioOrganizacion.guardar(organizacionBuscado);

        response.redirect("/organizacion/"+request.params("idOrganizacion")+"/todoOk");
        return response;
    }


    public static ModelAndView pantallaCargaExcel(Request request, Response response) {
        RepositorioOrganizacion repositorioOrganizacion= new RepositorioOrganizacion();

        String idOrganizaci??n = request.params("idOrganizacion");
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(Integer.parseInt(idOrganizaci??n));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscado);
        }},"registrarMediciones.hbs");

    }

    public static String todoOk(Request request, Response response) {
        return "todo ok. Archivo cargado";
    }


}
