package trayecto;

import AgenteSectorial.Territorio;
import EntidadPersistente.EntidadPersistente;
import Organizacion.Organizacion;
import db.EntityManagerHelper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "provincia")
public class Provincia extends Territorio {
    @Column(name = "provincia")
    private String provincia;


    public Provincia(String provincia) {
        this.provincia = provincia;
    }

    public Provincia() {}

}
