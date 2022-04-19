package Main;

import Usuarios.Administrador;
import Usuarios.Usuario;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hola mundo");
        Administrador usuario = new Administrador("nacho", "123");
        System.out.println(usuario.getNombre());
    }

}
