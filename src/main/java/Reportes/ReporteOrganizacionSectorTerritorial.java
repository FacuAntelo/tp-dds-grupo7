package Reportes;

import AgenteSectorial.SectorTerritorial;
import HuellaDeCarbono.HuellaDeCarbono;
import Organizacion.Organizacion;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class ReporteOrganizacionSectorTerritorial {
    private Organizacion organizacion;
    SectorTerritorial sectorTerritorial;
    private int id_sectorTerritorial;
    private int id;
    private LocalDate fecha;
    private HuellaDeCarbono valorHCTotal;

    public ReporteOrganizacionSectorTerritorial(Organizacion organizacion, int id_sectorTerritorial, int id, LocalDate fecha, HuellaDeCarbono valorHCTotal) {
        this.organizacion = organizacion;
        this.id_sectorTerritorial = id_sectorTerritorial;
        this.id = id;
        this.fecha = fecha;
        this.valorHCTotal = valorHCTotal;
    }
    public ReporteOrganizacionSectorTerritorial( SectorTerritorial sectorTerritorial, int id, LocalDate fecha, HuellaDeCarbono valorHCTotal) {
        this.sectorTerritorial = sectorTerritorial;
        this.id = id; 
        this.fecha = fecha;
        this.valorHCTotal = valorHCTotal;
    }
}
