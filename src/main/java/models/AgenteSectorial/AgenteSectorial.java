package models.AgenteSectorial;

import models.EntidadPersistente.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;
import models.Usuarios.Usuario;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "agente_sectorial")
public class AgenteSectorial extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sector_territorial",referencedColumnName = "id")
    private SectorTerritorial sectorTerritorial;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
//    private Provincia provincia;
//    private Localidad localidad;

    public AgenteSectorial(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
//        this.provincia = provincia;
//        this.localidad = localidad;
    }

    public AgenteSectorial(String nombre, String apellido, SectorTerritorial sectorTerritorial, Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sectorTerritorial = sectorTerritorial;
        this.usuario = usuario;
    }
}
