package models.trayecto;

import models.AgenteSectorial.Territorio;
import models.HuellaDeCarbono.RegistroHC;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Entity
@Table(name = "provincia")
public class Provincia extends Territorio {
    @Column(name = "provincia")
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_provincia", referencedColumnName = "id")
    private List<Localidad> localidades;


    public Provincia(String provincia) {
        this.nombre = provincia;
    }

    public Provincia() {}

    public RegistroHC calcularHC(){
        List<RegistroHC> registros = localidades.stream().map(l -> l.calcularHC()).collect(Collectors.toList());
        return RegistroHC.unificarRegistros(registros);
    }

}
