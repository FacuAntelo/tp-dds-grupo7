package TestPeticion;

import models.Organizacion.Peticion;
import models.db.EntityManagerHelper;
import org.junit.Assert;
import org.junit.Test;
import repositories.RepositorioPeticion;

import java.util.List;
import java.util.stream.Collectors;

public class TestPeticion {
    @Test
    public void persistirPeticion(){
        RepositorioPeticion repositorioPeticion = new RepositorioPeticion();
        Peticion peticion= new Peticion();
        peticion.setNombre("emily");
        peticion.setApellido("higa");
        peticion.setNumDoc("12345");
        peticion.setEmail("emily@");
        repositorioPeticion.guardar(peticion);
    }

    @Test
    public void obtenerTodasLasPeticiones(){

        List<Peticion> peticionList = EntityManagerHelper.getEntityManager().createQuery("from Peticion",Peticion.class)
                .getResultList();
        peticionList= peticionList.stream().filter(x -> x.getOrganizacion().getId()==1).collect(Collectors.toList());
        Assert.assertEquals(10,peticionList.size());
    }
}
