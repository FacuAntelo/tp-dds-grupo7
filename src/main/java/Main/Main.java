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

import java.util.List;


public class Main {
    public static void main(String[] args) {
        ValidadorDePassword vali = new ValidadorDePassword();
        CriterioValidador validacionPorLongitud = new Validador.ValidacionPorLongitud();
        vali.agregarCriterio(validacionPorLongitud);

        /*System.out.println("Hola mundo");
        */Administrador usuario = new Administrador("nacho555555", "123asdsadasdsad");
        System.out.print(usuario.validarClave(vali));

        /*Prueba de miembro*/

        Organizacion org = new Organizacion();
        Sector sec = new Sector("market", org);
        org.agregarSector(sec);
        System.out.print(sec.getNombre());

        Persona per = new Persona();
        per.setNombre("Nacho");
        System.out.print(per.getNombre());

        APIInterna api = new APIInterna();

        //System.out.print(per);
        org.darDeAltaMiembro2(per, api, sec);

        //System.out.print(per);

        sec.getMiembros().forEach(un_miembro -> {System.out.print(un_miembro.getPersona().getNombre());} );
        //sadsadasdas

    }

}
