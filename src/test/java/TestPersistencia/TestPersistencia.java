package TestPersistencia;

import Usuarios.FactorDeEmision;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestPersistencia {
    EntityManagerFactory emf;
    EntityManager em;

    @Before
    public void inicializacion() throws IOException {
        emf = Persistence.createEntityManagerFactory("db");
        em = emf.createEntityManager();
    }

    @Test
    public  void persistirFactorDeEmision() throws IOException {
        inicializacion();
        FactorDeEmision naftaFactorDeEmision = new FactorDeEmision("NAFTA",300,"lts");
        FactorDeEmision gasFactorDeEmision = new FactorDeEmision("GAS NATURAL",301,"m3");

        em.getTransaction().begin();
        em.persist(naftaFactorDeEmision);
        em.persist(gasFactorDeEmision);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void recuperarFactorEmision(){
        List<FactorDeEmision> resultado= (List<FactorDeEmision>) em.createQuery("from FactorDeEmision ").getResultList();
        System.out.print(resultado.size());
        Assert.assertEquals(36,resultado.size(),0) ;
    }

    @Test
    public void recuperarUnFactorDeEmision(){
        FactorDeEmision resultado = (FactorDeEmision) em.createQuery("from FactorDeEmision where valorFactorEmision=300").getSingleResult();
        System.out.print(resultado.getId());
        Assert.assertEquals(35,resultado.getId()) ;
    }
}
