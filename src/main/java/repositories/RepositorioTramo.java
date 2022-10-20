package repositories;

import models.Organizacion.Organizacion;
import models.db.EntityManagerHelper;
import models.trayecto.Tramo;
import models.trayecto.Trayecto;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

public class RepositorioTramo {
    public void guardar(Tramo... tramos){
        EntityManagerHelper.beginTransaction();
        List<Tramo> tramosList = Arrays.asList(tramos);
        tramosList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public void eliminar(Tramo... tramos){
        EntityManagerHelper.beginTransaction();
        List<Tramo> tramosList = Arrays.asList(tramos);
        tramosList.forEach(EntityManagerHelper.entityManager()::remove);
        EntityManagerHelper.commit();
    }

    public void actualizar(Tramo... tramos){
        EntityManagerHelper.beginTransaction();
        List<Tramo> tramosList = Arrays.asList(tramos);
        tramosList.forEach(EntityManagerHelper.entityManager()::refresh);
        EntityManagerHelper.commit();
    }

    public List<Tramo> buscarTodos(int idTrayecto){
        return EntityManagerHelper.getEntityManager()
                .createQuery("from Tramo as t where t.trayecto_id = :idTrayecto", Tramo.class)
                .setParameter("idTrayecto", idTrayecto)
                .getResultList();
    }

    public Tramo buscar(int id){
        return EntityManagerHelper.getEntityManager().find(Tramo.class,id);
    }
}
