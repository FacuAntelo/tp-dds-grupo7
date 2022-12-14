package models.Miembro;
import models.EntidadPersistente.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;
import models.Organizacion.Organizacion;
import models.Usuarios.Usuario;
import models.trayecto.Tramo;
import models.trayecto.Trayecto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "miembro")
@Setter
@Getter
public class Miembro extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento")
    private String numDoc;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "miembro_id", referencedColumnName = "id")
    private List<Trayecto> trayectos;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Miembro(){}

    public Miembro(String nombre, String apellido, TipoDocumento tipoDocumento, String numDoc) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.numDoc = numDoc;
        this.trayectos = new ArrayList<>();
    }

    public void agregarTrayecto(Trayecto trayecto, Organizacion organizacion){
        trayectos.add(trayecto);
        organizacion.agregarTrayecto(trayecto);
    }

    public List<Tramo> getTramos(){
        return trayectos.stream().flatMap(t -> t.getTramos().stream()).collect(Collectors.toList());
    }

}
