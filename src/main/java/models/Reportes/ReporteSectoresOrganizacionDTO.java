package models.Reportes;

import lombok.Getter;
import lombok.Setter;
import models.HuellaDeCarbono.HuellaDeCarbono;
import models.Sector.Sector;
@Getter
@Setter
public class ReporteSectoresOrganizacionDTO {

    String sector;
    String valorHCconUnidad;

    public ReporteSectoresOrganizacionDTO(String sector, String valorHCconUnidad) {
        this.sector = sector;
        this.valorHCconUnidad = valorHCconUnidad;
    }

}
