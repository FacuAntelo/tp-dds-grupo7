package Reportes;

import HuellaDeCarbono.HuellaDeCarbono;
import Organizacion.Organizacion;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class ReporteOrganizacionSectorTerritorial {
    private Organizacion organizacion;
    private int id;
    private LocalDate fecha;
    private HuellaDeCarbono valorHCTotal;

    public ReporteOrganizacionSectorTerritorial(Organizacion organizacion, int id, LocalDate fecha, HuellaDeCarbono valorHCTotal) {
        this.organizacion = organizacion;
        this.id = id;
        this.fecha = fecha;
        this.valorHCTotal = valorHCTotal;
    }
}
