package models.Reportes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteNombreValor {
    private String nombre;
    private String valor;

    public ReporteNombreValor(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }
}
