package AgenteSectorial;

import lombok.Getter;
import lombok.Setter;
import trayecto.Localidad;
import trayecto.Provincia;

import java.util.List;

@Getter
@Setter
public class AgenteSectorial {

    private String nombre;
    private String apellido;
    private List<SectorTerritorial> sectoresTerritoriales;
//    private Provincia provincia;
//    private Localidad localidad;

    public AgenteSectorial(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
//        this.provincia = provincia;
//        this.localidad = localidad;
    }
}
