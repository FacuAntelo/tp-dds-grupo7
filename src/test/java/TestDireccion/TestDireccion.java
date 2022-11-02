package TestDireccion;

import models.db.EntityManagerHelper;
import models.trayecto.Direccion;
import models.trayecto.Localidad;
import models.trayecto.Provincia;
import org.junit.Test;
import repositories.RepositorioLocalidad;
import repositories.RepositorioProvincia;

import java.util.List;
import java.util.stream.Collectors;

public class TestDireccion {
    @Test
    public void direccion(){
        RepositorioLocalidad repositorioLocalidad=new RepositorioLocalidad();
        RepositorioProvincia repositorioProvincia = new RepositorioProvincia();

        Provincia provincia= repositorioProvincia.buscarPorId(70);
        Localidad localidad = repositorioLocalidad.buscarPorId(Long.parseLong("70035040002"));
        List<Direccion> direccionList = EntityManagerHelper.getEntityManager()
                .createQuery("select d from Direccion d where d.calle= :calle and d.altura= :altura",Direccion.class)
                .setParameter("calle", "1")
                .setParameter("altura", 2)
                .getResultList()
                .stream().filter(d-> d.getLocalidad()==localidad && d.getProvincia()==provincia).collect(Collectors.toList());
        System.out.println(direccionList.size());
    }
}
