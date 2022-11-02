package models.DTO;

import lombok.Getter;
import lombok.Setter;
import models.HuellaDeCarbono.TipoRegistro;

import javax.persistence.Column;
import java.time.LocalDate;

@Setter
@Getter
public class RegistroDTO {
    private LocalDate fecha;
    private TipoRegistro tipoRegistro;
    private int valorDA;
    private int valorTrayecto;
    private int valorTotal;
    private String unidad = "KGCO2eq";

    public RegistroDTO(LocalDate fecha, TipoRegistro tipoRegistro, int valorDA, int valorTrayecto, int valorTotal){
        this.fecha = fecha;
        this.tipoRegistro=tipoRegistro;
        this.valorDA=valorDA;
        this.valorTrayecto = valorTrayecto;
        this.valorTotal = valorTotal;
    }
}
