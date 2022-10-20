package models.EntidadPersistente;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class EntidadPersistente {
    @Id
    @GeneratedValue
    private int id;
}
