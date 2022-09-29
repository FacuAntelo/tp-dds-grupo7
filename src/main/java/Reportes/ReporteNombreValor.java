package Reportes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteNombreValor {
    private String nombre;
    private Long valor;

    public ReporteNombreValor(String nombre, Long valor) {
        this.nombre = nombre;
        this.valor = valor;
    }
}
