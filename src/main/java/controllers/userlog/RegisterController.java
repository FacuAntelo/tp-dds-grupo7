package controllers.userlog;


import com.google.gson.Gson;
import models.Usuarios.Usuario;
import models.Validador.ValidadorDePassword;
import repositories.RepositorioUsuario;
import spark.ModelAndView;
import spark.Redirect;
import spark.Request;
import spark.Response;

import java.util.Properties;

public class RegisterController {

    public ModelAndView getPantallaRegister(Request request, Response response) {
        return new ModelAndView(null, "login/register.hbs");
    }
//    public ModelAndView pantallaDeLogin(Request request, Response response) {
//        return new ModelAndView(null, "login.hbs");
//    }

    public Response obtenerDatos(Request request, Response response){
//        Gson gson = new Gson();
        RepositorioUsuario repoUsuario = new RepositorioUsuario();
        ValidadorDePassword validadorDePassword = new ValidadorDePassword();

//        Properties p = gson.fromJson(request.body(),Properties.class);
        Usuario usuario = new Usuario();

        usuario.setNombre(request.queryParams("nombre"));
        usuario.setApellido(request.queryParams("apellido"));
        usuario.setEmail(request.queryParams("email"));
        usuario.setNombreDeUsuario(request.queryParams("usuario"));
        usuario.setContrasenia(request.queryParams("password"));



        if(validadorDePassword.esValida(usuario.getContrasenia()) && !repoUsuario.existeUsuarioConNombreUsuario(usuario.getNombreDeUsuario())) {
            repoUsuario.guardar(usuario);
            response.redirect("/register/sucess");
        } else {
            response.redirect("/register");

        }
        return response;
    }

    public static ModelAndView succesRegister(Request request, Response response) {
        return new ModelAndView(null, "login/registerSucess.hbs");
    }
}
