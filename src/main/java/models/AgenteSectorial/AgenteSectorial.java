package models.AgenteSectorial;

import models.EntidadPersistente.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

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
    @JoinColumn(name = "id_sector",referencedColumnName = "id")
    private SectorTerritorial sectorTerritorial;
//    private Provincia provincia;
//    private Localidad localidad;

    public AgenteSectorial(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
//        this.provincia = provincia;
//        this.localidad = localidad;
    }
}
