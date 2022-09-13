package trayecto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
//
@Embeddable
@Getter
@Setter
public class Localidad {
    @Column(name = "localidad")
    private int localidad;

    public Localidad(int localidad) {this.localidad = localidad;}

    public int getNumeroLocalidad(){
        return localidad;
    }

}
