package models.trayecto;

import models.AgenteSectorial.Territorio;
import models.HuellaDeCarbono.RegistroHC;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Entity
@Table(name = "provincia")
public class Provincia extends Territorio implements Serializable {

    @Column(name = "id_provincia")
    private long id;

    @Column(name = "provincia")
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY,cascade =CascadeType.ALL/*,mappedBy = "provincia"*/)
    @JoinColumn(name = "id_provincia",referencedColumnName = "id_provincia")
    private List<Localidad> localidades;

    public Provincia(String provincia) {
        this.nombre = provincia;
        this.localidades = new ArrayList<>();
    }

    public Provincia() {}

    public RegistroHC calcularHC(){
        List<RegistroHC> registros = localidades.stream().map(l -> l.calcularHC()).collect(Collectors.toList());
        return RegistroHC.unificarRegistros(registros);
    }

}
