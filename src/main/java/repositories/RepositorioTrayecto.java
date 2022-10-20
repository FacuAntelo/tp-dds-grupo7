package repositories;

import models.Organizacion.Organizacion;
import models.db.EntityManagerHelper;
import models.trayecto.Trayecto;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

public class RepositorioTrayecto {

    public void guardar(Trayecto... trayecto){
        EntityManagerHelper.beginTransaction();
        List<Trayecto> trayectoList = Arrays.asList(trayecto);
        trayectoList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public void eliminar(Trayecto... trayecto){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        List<Trayecto> trayectoList = Arrays.asList(trayecto);
        trayectoList.forEach(entityManager::remove);
        entityManager.getTransaction().commit();
    }

    public void actualizar(Trayecto... trayecto){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        List<Trayecto> trayectoList = Arrays.asList(trayecto);
        trayectoList.forEach(entityManager::refresh);
        entityManager.getTransaction().commit();
    }

    public List<Trayecto> buscarTodos(Integer idMiembro){
        return (List<Trayecto>) EntityManagerHelper.getEntityManager().
                createQuery("from Trayecto as t where t.id = :idMiembro",Trayecto.class).
                setParameter("idMiembro", idMiembro).getResultList();
    }

    public Organizacion buscar(int id){
        return EntityManagerHelper.getEntityManager().find(Organizacion.class,id);
    }
}

