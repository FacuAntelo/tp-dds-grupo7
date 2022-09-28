package Reportes;

import Miembro.Miembro;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportePorMiembro {
    private String miembro;
    private Long valor;

    public ReportePorMiembro(String miembro, Long valor) {
        this.miembro = miembro;
        this.valor = valor;
    }
}
