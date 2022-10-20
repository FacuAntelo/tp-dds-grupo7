package models.Reportes;

import lombok.Getter;
import lombok.Setter;
import models.trayecto.Provincia;

@Getter
@Setter
public class ReportePorProvincia {
    private Provincia provincia;
    private Long valor;

    public ReportePorProvincia(Provincia provincia, Long valor) {
        this.provincia = provincia;
        this.valor = valor;
    }
}
