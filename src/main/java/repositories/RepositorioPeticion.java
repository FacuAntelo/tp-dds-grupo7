package repositories;

import models.Organizacion.Organizacion;
import models.Organizacion.Peticion;
import models.db.EntityManagerHelper;

import java.util.Arrays;
import java.util.List;

public class RepositorioPeticion {
    public void guardar(Peticion... peticion){
        EntityManagerHelper.beginTransaction();
        List<Peticion> organizacionList = Arrays.asList(peticion);
        organizacionList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public Peticion findByID(Long id){
        return EntityManagerHelper.getEntityManager().find(Peticion.class,id);
    }
}
