package models.Organizacion;

import lombok.Getter;
import lombok.Setter;
import models.EntidadPersistente.EntidadPersistente;
import models.Miembro.Miembro;
import models.Miembro.TipoDocumento;
import models.Sector.Sector;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "peticion")
public class Peticion extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;
    @Column(name = "numDoc")
    private String numDoc;
    @Column(name = "email")
    private String email;
    @OneToOne(fetch = FetchType.LAZY)
    private Organizacion organizacion;

    @Enumerated(EnumType.STRING)
    private EstadoPeticion estadoPeticion;

    public Miembro aceptarPeticion(Sector sector){
        Miembro nuevoMiembro = new Miembro(nombre, apellido, tipoDocumento,numDoc);
        this.organizacion.agregarMiembroASector(sector, nuevoMiembro);

        return nuevoMiembro;
    }

}