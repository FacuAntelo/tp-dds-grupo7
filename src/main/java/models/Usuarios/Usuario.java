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

    @Column
    private String nombreDeUsuario;

    @Column
    private String contrasenia;

    @OneToOne(fetch = FetchType.EAGER)
    private Miembro miembro;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    public Usuario(String usuario, String contrasenia) {
        super();
    }

    public boolean validarClave(Validable validable){
        return validable.esValida(this.getContrasenia());
    }
}
