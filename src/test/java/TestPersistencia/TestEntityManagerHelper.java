package TestPersistencia;

import Combustible.Combustible;
import Organizacion.Clasificacion;
import Organizacion.Organizacion;
import Organizacion.TipoOrganizacion;
import Organizacion.Ubicacion;
import Usuarios.FactorDeEmision;
import db.EntityManagerHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import trayecto.Direccion;
import trayecto.Localidad;
import trayecto.Provincia;
import unidad.KG;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

import static Organizacion.TipoOrganizacion.EMPRESA;

public class TestEntityManagerHelper {
    @Test
    public  void persistirFactorDeEmision() throws IOException {

        FactorDeEmision naftaFactorDeEmision = new FactorDeEmision("NAFTA",400,"lts");
        FactorDeEmision gasFactorDeEmision = new FactorDeEmision("GAS NATURAL",400,"m3");

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(naftaFactorDeEmision);
        EntityManagerHelper.getEntityManager().persist(gasFactorDeEmision);
        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }

    @Test
    public void recuperarUnFactorDeEmision(){
        FactorDeEmision resultado = (FactorDeEmision) EntityManagerHelper.getEntityManager().createQuery("from FactorDeEmision where valorFactorEmision=300").getSingleResult();
        System.out.print(resultado.getId());
        Assert.assertEquals(35,resultado.getId()) ;
    }

    @Test
    public void persistirCombustible(){
        FactorDeEmision naftaFactorDeEmision = new FactorDeEmision("NAFTA",400,"lts");
        Combustible nafta = new Combustible("nafta");
        nafta.setFactorEmision(naftaFactorDeEmision);
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(naftaFactorDeEmision);
        EntityManagerHelper.getEntityManager().persist(nafta);
        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }

    @Test
    public void pruebaJoin(){
        int algo = 45;
        EntityManagerHelper.beginTransaction();
        List <Combustible> resultado = (List <Combustible>) EntityManagerHelper.getEntityManager().createQuery("SELECT c from Combustible as c left join c.factorEmision as fe where c.factorEmision.id =:algo", Combustible.class).setParameter("algo", 45).getResultList();
        System.out.print(resultado.size());
    }


}
