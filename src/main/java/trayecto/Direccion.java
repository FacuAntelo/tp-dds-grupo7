package trayecto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Direccion {
    private String calle;
    private int altura;
    private Localidad localidad;
    private Provincia provincia;

    public Direccion(String calle, int altura, Localidad localidad, Provincia provincia) {
        this.calle = calle;
        this.altura = altura;
        this.localidad = localidad;
        this.provincia = provincia;
    }

}
