package Main;

import Miembro.Persona;
import Organizacion.Organizacion;
import Sector.Sector;
import Usuarios.Administrador;
import Usuarios.Usuario;
import ValidacionExterna.APIInterna;
import Validador.CriterioValidador;
import Validador.Validable;
import Validador.ValidadorDePassword;
import Miembro.*;


public class Main {
    public static void main(String[] args) {
        ValidadorDePassword vali = new ValidadorDePassword();
        CriterioValidador validacionPorLongitud = new Validador.ValidacionPorLongitud();
        vali.agregarCriterio(validacionPorLongitud);

        System.out.println("Hola mundo");
        Administrador usuario = new Administrador("nacho555555", "123asdsadasdsad");
        System.out.print(usuario.validarClave(vali));

        /*Prueba de miembro*/

        Organizacion org = new Organizacion();
        Sector sec = new Sector("Mark", org);
        org.agregarSector(sec);
        Persona per = new Persona();
        per.setNombre("Jose");
        APIInterna api = new APIInterna();
        System.out.print(per.tipoPerfil.queSoy());
        org.darDeAltaMiembro(per, api);

        System.out.print(per.tipoPerfil.queSoy());
        System.out.print(sec.getMiembros());

    }

}
