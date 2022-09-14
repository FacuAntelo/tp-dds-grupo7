package Miembro;
import EntidadPersistente.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;
import trayecto.*;
import Organizacion.Organizacion;

import javax.persistence.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany
    @JoinColumn(name = "miembro_id", referencedColumnName = "id")
    private List<Trayecto> trayectos;



    public Miembro(String nombre, String apellido, TipoDocumento tipoDocumento, String numDoc) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.numDoc = numDoc;
        this.trayectos = new ArrayList<>();
    }

    public void agregarTrayecto(Trayecto trayecto,Organizacion organizacion){
        trayectos.add(trayecto);
        organizacion.agregarTrayecto(trayecto);
    }



    public Double calcularHC(){
        return this.trayectos.stream().mapToDouble(trayecto -> trayecto.getHCTotalTramos()).sum();
        //Traer datos de da del exel + lista de fe del usuario administrador(Falta crear) que incorpora los datos de un txt
        //Tener en cuenta que el calculo HC Por miembro es de la suma de sus trayectos. A eso debemos sumar las actividades del exel
        //La huella de carbono de cada tramo hay que agregarsela al miembro propietario del tramo
        //Tener en cuenta cuando un miembro pertence a varias organizaciones, el calculo de hc por organizacion puede fallar
        //Como saber si pertenece el tramo a la organizacion? Facil direccionFinalTramo == direccionOrganizacionCalle
    }

}
