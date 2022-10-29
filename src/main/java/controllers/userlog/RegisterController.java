package controllers.userlog;


import com.google.gson.Gson;
import models.Miembro.TipoDocumento;
import models.Usuarios.Usuario;
import models.Validador.ValidadorDePassword;
import repositories.RepositorioUsuario;
import spark.ModelAndView;
import spark.Redirect;
import spark.Request;
import spark.Response;

import java.util.*;
import java.util.stream.Collectors;

public class RegisterController {

    public ModelAndView getPantallaRegister(Request request, Response response) {
//        List<TipoDocumento> tipoDocumentoList = Arrays.asList(TipoDocumento.values());
        List<String> tipoDocumentoList= Arrays.stream(TipoDocumento.values()).map(x-> x.name()).collect(Collectors.toList());
        return new ModelAndView(new HashMap<String, Object>(){{
            put("tipoDocumentos", tipoDocumentoList);
        }},"login/register.hbs");
    }
//    public ModelAndView pantallaDeLogin(Request request, Response response) {
//        return new ModelAndView(null, "login.hbs");
//    }

    public Response registrarUsuario(Request request, Response response){
        RepositorioUsuario repoUsuario = new RepositorioUsuario();
        ValidadorDePassword validadorDePassword = new ValidadorDePassword();

        Usuario usuario = new Usuario();
        usuario.setNombre(request.queryParams("nombre"));
        usuario.setApellido(request.queryParams("apellido"));
        usuario.setTipoDocumento(Enum.valueOf(TipoDocumento.class,request.queryParams("tipoDocumento")));
        usuario.setNumeroDocumento(request.queryParams("nroDocumento"));
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
