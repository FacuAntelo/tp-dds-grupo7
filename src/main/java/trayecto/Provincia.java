package trayecto;

import AgenteSectorial.Territorio;
import EntidadPersistente.EntidadPersistente;
import HuellaDeCarbono.RegistroHC;
import Organizacion.Organizacion;
import db.EntityManagerHelper;
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
    private String provincia;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_provincia", referencedColumnName = "id")
    private List<Localidad> localidades;


    public Provincia(String provincia) {
        this.provincia = provincia;
    }

    public Provincia() {}

    public RegistroHC calcularHC(){
        List<RegistroHC> registros = localidades.stream().map(l -> l.calcularHC()).collect(Collectors.toList());
        return RegistroHC.unificarRegistros(registros);
    }

}
