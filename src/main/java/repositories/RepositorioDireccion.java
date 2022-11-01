package repositories;

import models.Organizacion.Organizacion;
import models.db.EntityManagerHelper;
import models.trayecto.Direccion;
import models.trayecto.Localidad;
import models.trayecto.Provincia;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDireccion {
    public void guardar(Direccion... direcciones){
        EntityManagerHelper.beginTransaction();
        List<Direccion> direccionList = Arrays.asList(direcciones);
        direccionList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }
    public Direccion buscar(Integer id){
        return EntityManagerHelper.getEntityManager().find(Direccion.class,id);
    }

    public Direccion buscarYGuardar(String calle, int altura, Localidad localidad, Provincia provincia){
        Direccion direccionARetornar ;

        List<Direccion> direcciones = EntityManagerHelper.getEntityManager()
                .createQuery("select d from Direccion d where d.calle= :calle and d.altura= :altura",Direccion.class)
                .setParameter("calle", calle)
                .setParameter("altura", altura)
                .getResultList()
                .stream().filter(d-> d.getLocalidad()==localidad && d.getProvincia()==provincia).collect(Collectors.toList());
        if(direcciones.isEmpty()){
            direccionARetornar=new Direccion(calle,altura,localidad,provincia);
            this.guardar(direccionARetornar);
        }
        else {
            direccionARetornar= direcciones.get(0);
        }
        return direccionARetornar;

    }
}
