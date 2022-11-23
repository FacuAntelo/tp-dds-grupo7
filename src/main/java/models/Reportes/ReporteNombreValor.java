package models.Reportes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteNombreValor {
    private String nombre;
    private String valor;

    private String datoExtra;

    public ReporteNombreValor(String nombre, String valor, String datoExtra) {
        this.nombre = nombre;
        this.valor = valor;
        this.datoExtra = datoExtra;
    }
}
