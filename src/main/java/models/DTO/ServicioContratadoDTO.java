package models.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicioContratadoDTO {
    private int id;
    private String nombre;

    public ServicioContratadoDTO(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }



}
