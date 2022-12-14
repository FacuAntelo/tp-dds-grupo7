package models.Usuarios;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "administrador")
public class Administrador extends Usuario{
    @Transient
    List<FactorDeEmision> facEmisiones;

    @Transient
    List<String> Fes = new ArrayList<String>();

    public Administrador(String usuario, String contrasenia){
        super(usuario, contrasenia);
        facEmisiones = new ArrayList<>();
    }

    public Administrador() {
    }
/*    public void FactorDeEmision() throws FileNotFoundException {
        File doc = new File("C:\\Users\\jose_\\OneDrive\\Escritorio\\2022-mi-no-mino-grupo-07\\src\\main\\java\\Usuarios\\factoresDeEmision.config");
        Scanner obj = new Scanner(doc);
        while (obj.hasNextLine()) {
            String aux = obj.nextLine();
            String []aux2 = aux.split(" ");
            FactorDeEmision fac = new FactorDeEmision(aux2[0],Integer.parseInt(aux2[1]),aux2[2]);
            facEmisiones.add(fac);
            System.out.println("nombre factor"+fac.getNombreFactor()+"valor del factor"+fac.getFactorEmision()+"unidad del factor:"+fac.getUnidad());
        }

    }*/

    public List<FactorDeEmision> getFactorDeEmisiones(){
        return this.facEmisiones;
    }
}
