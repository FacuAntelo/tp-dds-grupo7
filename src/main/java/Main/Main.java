package Main;

import CargaExcel.ExcelUtils;
import Usuarios.Administrador;
import Usuarios.Usuario;
import Validador.CriterioValidador;
import Validador.ValidadorDePassword;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String path = new String("C:\\Users\\facua\\Desktop\\TP DDS\\2022-mi-no-mino-grupo-07\\src\\main\\java\\Main\\Libro1.xlsx");
        System.out.println("Hola mundo");
        Administrador usuario = new Administrador("nacho", "123");
        System.out.println(usuario.getNombre());
        ExcelUtils.leerExcel(path);

    }

}
