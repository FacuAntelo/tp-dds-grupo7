package AgenteSectorial;

import HuellaDeCarbono.RegistroHC;
import Organizacion.Organizacion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class SectorTerritorial {
    private List<Organizacion> organizaciones;
    private List<RegistroHC> registrosHC = new ArrayList<>();

    public void generarReporte(){}

}
