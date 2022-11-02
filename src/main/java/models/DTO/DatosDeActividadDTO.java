package models.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosDeActividadDTO {
    private int id;
    private String actividad;
    private String tipoDeConsumo;
    private String valorFactorEmisionConUnicad;

    public DatosDeActividadDTO(int id, String actividad, String tipoDeConsumo, String valorFactorEmisionConUnicad) {
        this.id = id;
        this.actividad = actividad;
        this.tipoDeConsumo = tipoDeConsumo;
        this.valorFactorEmisionConUnicad = valorFactorEmisionConUnicad;
    }

}
