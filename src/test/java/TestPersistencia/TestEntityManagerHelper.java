package TestPersistencia;

import Usuarios.FactorDeEmision;
import db.EntityManagerHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import unidad.KG;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class TestEntityManagerHelper {
    @Before
    public void inicializacion() throws IOException{
        EntityManagerHelper.getEntityManagerHelper();
    }
    @Test
    public  void persistirFactorDeEmision() throws IOException {
        inicializacion();
        FactorDeEmision naftaFactorDeEmision = new FactorDeEmision("NAFTA",400,"lts");
        FactorDeEmision gasFactorDeEmision = new FactorDeEmision("GAS NATURAL",400,"m3");

        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(naftaFactorDeEmision);
        EntityManagerHelper.getEntityManager().persist(gasFactorDeEmision);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
        EntityManagerHelper.getEntityManager().close();
    }

    @Test
    public void recuperarUnFactorDeEmision(){
        FactorDeEmision resultado = (FactorDeEmision) EntityManagerHelper.getEntityManager().createQuery("from FactorDeEmision where valorFactorEmision=300").getSingleResult();
        System.out.print(resultado.getId());
        Assert.assertEquals(35,resultado.getId()) ;
    }
}
