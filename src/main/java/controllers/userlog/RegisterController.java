package controllers.userlog;


import com.google.gson.Gson;
import models.Usuarios.Usuario;
import models.Validador.ValidadorDePassword;
import repositories.RepositorioUsuario;
import spark.Redirect;
import spark.Request;
import spark.Response;

import java.util.Properties;

public class RegisterController {

    public static String getPantallaRegister(Request request, Response response) {
        return "registrese señor, porfa";
    }

    public static Response obtenerDatos(Request request, Response response){
        Gson gson = new Gson();
        RepositorioUsuario repoUsuario = new RepositorioUsuario();
        ValidadorDePassword validadorDePassword = new ValidadorDePassword();

        Properties p = gson.fromJson(request.body(),Properties.class);
        Usuario usuario = new Usuario();

        usuario.setNombre(p.getProperty("nombre"));
        usuario.setApellido(p.getProperty("apellido"));
        usuario.setEmail(p.getProperty("email"));
        usuario.setNombreDeUsuario(p.getProperty("nombre de usuario"));
        usuario.setContrasenia(p.getProperty("contrasenia"));


        if(validadorDePassword.esValida(usuario.getContrasenia()) && !repoUsuario.existeUsuarioConNombreUsuario(usuario.getNombreDeUsuario())) {
            repoUsuario.guardar(usuario);
            response.redirect("/register/sucess");
        } else {
            response.redirect("/register");

        }
        return response;
    }

    public static String succesRegister(Request request, Response response) {
        return "todo OK por aki, muy bien muchachito";
    }
}
