package repositories;

import models.Organizacion.Organizacion;
import models.Organizacion.Peticion;
import models.Sector.Sector;
import models.db.EntityManagerHelper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioPeticion {
    public void guardar(Peticion... peticion){
        EntityManagerHelper.beginTransaction();
        List<Peticion> peticionList = Arrays.asList(peticion);
        peticionList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }
    public List<Peticion> buscarTodos(int idOrganizacion){
        List<Peticion> peticionList = EntityManagerHelper.getEntityManager().createQuery("from Peticion",Peticion.class).getResultList();
        peticionList = peticionList.stream().filter(x -> x.getOrganizacion().getId()==idOrganizacion).collect(Collectors.toList());

        return peticionList;
    }

    public Peticion findByID(Long id){
        return EntityManagerHelper.getEntityManager().find(Peticion.class,id);
    }
}
