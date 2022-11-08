package models.Usuarios;

import models.EntidadPersistente.EntidadPersistente;
import models.Miembro.Miembro;
import models.Miembro.TipoDocumento;
import models.Organizacion.Organizacion;
import models.Validador.Validable;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
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

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "usuario")
    private List<Miembro> miembros = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Organizacion> organizaciones = new ArrayList<>();

    public Usuario(){}

    public Usuario(String usuario, String contrasenia) {
        super();
    }

    public Usuario(String nombre, String apellido, TipoDocumento tipoDocumento, String numeroDocumento, String nombreDeUsuario, String contrasenia, String email, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombreDeUsuario = nombreDeUsuario;
        this.contrasenia = contrasenia;
        this.email = email;
        this.rol = rol;
    }

    public boolean validarClave(Validable validable){
        return validable.esValida(this.getContrasenia());
    }

    public Rol getRol() { return rol; }

    public void agregarMiembro(Miembro miembro) {
        miembros.add(miembro);
    }
}
