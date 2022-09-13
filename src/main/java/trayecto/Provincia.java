package trayecto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Provincia {
    @Column(name = "provincia")
    private String provincia;

    public Provincia(String provincia) {
        this.provincia = provincia;
    }

}
