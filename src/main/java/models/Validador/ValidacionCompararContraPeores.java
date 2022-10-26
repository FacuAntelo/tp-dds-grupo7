package models.Validador;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ValidacionCompararContraPeores extends CriterioValidador{
    public ValidacionCompararContraPeores() {
    }

    @Override
    public boolean esValida(String clave) throws FileNotFoundException {
        Path path = Paths.get("src/main/java/models/Validador/password-list-top-10000.txt");

        File archivo = new File(path.toAbsolutePath().toString());
        Scanner lector = new Scanner(archivo);

        while(lector.hasNextLine()){

            if(lector.nextLine().equals(clave)){
                return false;
            }
        }
        return true;
    }
}
