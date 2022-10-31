package models.db;

import models.Combustible.Combustible;
import models.Usuarios.FactorDeEmision;
import repositories.RepositorioCombustible;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersistenciaInicial {

    public static void persistirCombustibles(){
        RepositorioCombustible repositorioCombustible = new RepositorioCombustible();

        FactorDeEmision naftaFactorDeEmision = new FactorDeEmision("NAFTA", 10, "lts");
        Combustible nafta = new Combustible("nafta");
        nafta.setFactorEmision(naftaFactorDeEmision);

        FactorDeEmision gasFactorDeEmision = new FactorDeEmision("GAS NATURAL", 10, "m3");
        Combustible gas = new Combustible("gas");
        gas.setFactorEmision(gasFactorDeEmision);

        FactorDeEmision electricidadFactorDeEmision = new FactorDeEmision("ELECTRICIDAD", 50, "kWh");
        Combustible electricidad = new Combustible("electricidad");
        electricidad.setFactorEmision(electricidadFactorDeEmision);

        FactorDeEmision dieselFactorDeEmision = new FactorDeEmision("DIESEL", 50, "lts");
        Combustible diesel = new Combustible("diesel");
        diesel.setFactorEmision(dieselFactorDeEmision);

        List<Combustible> combustiblesAPErsistir = new ArrayList<>();
        combustiblesAPErsistir.add(nafta);
        combustiblesAPErsistir.add(gas);
        combustiblesAPErsistir.add(electricidad);
        combustiblesAPErsistir.add(diesel);

        List<String> combustiblesPersistidos = repositorioCombustible.buscarTodos().stream().map(c -> c.getNombre()).collect(Collectors.toList());

        combustiblesAPErsistir.forEach(c->{
            if(!combustiblesPersistidos.contains(c.getNombre())){
                repositorioCombustible.guardar(c);
            }
        });

    }
}
