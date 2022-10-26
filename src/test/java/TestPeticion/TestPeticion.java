package TestPeticion;

import models.Organizacion.EstadoPeticion;
import models.Organizacion.Peticion;
import models.db.EntityManagerHelper;
import org.junit.Assert;
import org.junit.Test;
import repositories.RepositorioPeticion;

import java.util.Arrays;
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

    @Test
    public void obtenerPeticion(){
        RepositorioPeticion repositorioPeticion = new RepositorioPeticion();
        System.out.println(repositorioPeticion.findByID(1).getId());
    }

    @Test
    public void actualizarPeticion(){
        RepositorioPeticion repositorioPeticion = new RepositorioPeticion();
        Peticion peticion = repositorioPeticion.findByID(8);
        peticion.setEstadoPeticion(EstadoPeticion.PENDIENTE);

        repositorioPeticion.actualizar(peticion);
    }

    @Test
    public void eliminarPeticion(){
        RepositorioPeticion repositorioPeticion = new RepositorioPeticion();
        Peticion peticion = repositorioPeticion.findByID(12);
        repositorioPeticion.eliminar(peticion);
    }
}
