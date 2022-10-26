package models.Usuarios;

import models.EntidadPersistente.EntidadPersistente;
import models.Miembro.Miembro;
import models.Validador.Validable;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Table(name = "usuario")
@Setter
@Getter
public class Usuario extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name ="usuario")
    private String nombreDeUsuario;

    @Column(name = "contraseña")
    private String contrasenia;

    @Column(name = "email")
    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    private Miembro miembro;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    public Usuario(){}

    public Usuario(String usuario, String contrasenia) {
        super();
    }

    public boolean validarClave(Validable validable){
        return validable.esValida(this.getContrasenia());
    }
}
