package models.db;

import models.Combustible.Combustible;
import models.Usuarios.FactorDeEmision;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaInicial {

    public void persistirCombistibles(){

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

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(gas);
        EntityManagerHelper.getEntityManager().persist(nafta);
        EntityManagerHelper.getEntityManager().persist(electricidad);
        EntityManagerHelper.getEntityManager().persist(diesel);
        EntityManagerHelper.commit();

    }
}
