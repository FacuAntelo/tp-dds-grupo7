package Reportes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportePorSector {
    private String sector;
    private Long valor;

    public ReportePorSector(String sector, Long valor) {
        this.sector = sector;
        this.valor = valor;
    }
}
