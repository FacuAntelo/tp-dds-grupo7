package models.db;

import models.Combustible.Combustible;
import models.MediosDeTransporte.TipoVehiculo;
import models.MediosDeTransporte.VehiculoParticular;
import models.Usuarios.FactorDeEmision;
import repositories.RepositorioCombustible;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersistenciaInicial {

    public static void persistirCombustibles(){
        RepositorioCombustible repositorioCombustible = new RepositorioCombustible();

        FactorDeEmision naftaFactorDeEmision = new FactorDeEmision("NAFTA", 10, "lts");
        Combustible nafta = new Combustible("Nafta");
        nafta.setFactorEmision(naftaFactorDeEmision);

        FactorDeEmision gasFactorDeEmision = new FactorDeEmision("GAS NATURAL", 10, "m3");
        Combustible gas = new Combustible("Gas");
        gas.setFactorEmision(gasFactorDeEmision);

        FactorDeEmision electricidadFactorDeEmision = new FactorDeEmision("ELECTRICIDAD", 50, "kWh");
        Combustible electricidad = new Combustible("Electricidad");
        electricidad.setFactorEmision(electricidadFactorDeEmision);

        FactorDeEmision dieselFactorDeEmision = new FactorDeEmision("DIESEL", 50, "lts");
        Combustible diesel = new Combustible("Diesel");
        diesel.setFactorEmision(dieselFactorDeEmision);

        List<Combustible> combustiblesAPErsistir = new ArrayList<>();
        combustiblesAPErsistir.add(nafta);
        combustiblesAPErsistir.add(gas);
        combustiblesAPErsistir.add(electricidad);
        combustiblesAPErsistir.add(diesel);

        List<String> nombreDeCombustiblesPersistidos = repositorioCombustible.buscarTodos().stream().map(c -> c.getNombre()).collect(Collectors.toList());

        combustiblesAPErsistir.forEach(c->{
            if(!nombreDeCombustiblesPersistidos.contains(c.getNombre())){
                repositorioCombustible.guardar(c);
            }
        });

//        //Modificar luego
        List<Combustible> combustiblesPersistidos = repositorioCombustible.buscarTodos().stream()
                .filter(c->c.getNombre()==nafta.getNombre()|| c.getNombre()== gas.getNombre() || c.getNombre()== electricidad.getNombre()|| c.getNombre()== diesel.getNombre())
                .collect(Collectors.toList());
        VehiculoParticular vehiculoParticularAutoCompartido = new VehiculoParticular(TipoVehiculo.AUTO,nafta,true);
        VehiculoParticular vehiculoParticularAutoNoCompartido= new VehiculoParticular(TipoVehiculo.AUTO,nafta,false);
//

    }
}
