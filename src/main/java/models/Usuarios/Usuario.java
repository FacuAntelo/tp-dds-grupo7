package models.Usuarios;

import models.EntidadPersistente.EntidadPersistente;
import models.Miembro.Miembro;
import models.Miembro.TipoDocumento;
import models.Validador.Validable;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Setter
@Getter
public class Usuario extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name ="usuario")
    private String nombreDeUsuario;

    @Column(name = "contraseña")
    private String contrasenia;

    @Column(name = "email")
    private String email;

    private Rol rol;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "usuario")
    private List<Miembro> miembros = new ArrayList<>();
//
////    @ManyToOne
////    @JoinColumn(name = "rol_id", referencedColumnName = "id")
//    @Transient
//    private Rol rol;

    public Usuario(){}

    public Usuario(String usuario, String contrasenia) {
        super();
    }

    public boolean validarClave(Validable validable){
        return validable.esValida(this.getContrasenia());
    }

    public Rol getRol() { return rol; }
}
