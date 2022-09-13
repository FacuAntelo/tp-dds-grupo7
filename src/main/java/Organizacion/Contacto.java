package Organizacion;

import EntidadPersistente.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name= "tipo_contacto")
@Table(name = "contacto")
@Setter
@Getter
public abstract class Contacto extends EntidadPersistente {
    @Column(name = "contacto")
    private String detalle;

    public abstract void serNotificado(String linkGuiaDeRecomendaciones, Date fecha);
    public abstract String obtenerContacto();
    public abstract HashMap<Date, String> obtenerNotificaciones();
}
