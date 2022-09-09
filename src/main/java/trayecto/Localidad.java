package trayecto;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Localidad {
    private int localidad;

    public Localidad(int localidad) {this.localidad = localidad;}

    public void setLocalidad(int localidad) {this.localidad = localidad;}

    public int getNumeroLocalidad() {
        return localidad;
    }
}
