package repositories;

import models.MediosDeTransporte.MediosDeTransporte;
import models.Miembro.Miembro;
import models.db.EntityManagerHelper;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

public class RepositorioMedioDeTransporte {
    public void guardar(MediosDeTransporte... mediosDeTransportes){
        EntityManagerHelper.beginTransaction();
        List<MediosDeTransporte> mediosDeTransportesList = Arrays.asList(mediosDeTransportes);
        mediosDeTransportesList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public void eliminar(MediosDeTransporte... mediosDeTransportes){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        List<MediosDeTransporte> mediosDeTransporteList = Arrays.asList(mediosDeTransportes);
        mediosDeTransporteList.forEach(entityManager::remove);
        entityManager.getTransaction().commit();
    }

    public void actualizar(MediosDeTransporte... mediosDeTransportes){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        List<MediosDeTransporte> mediosDeTransportesList = Arrays.asList(mediosDeTransportes);
        mediosDeTransportesList.forEach(entityManager::refresh);
        entityManager.getTransaction().commit();
    }



    public MediosDeTransporte buscar(int id){
        return EntityManagerHelper.getEntityManager().find(MediosDeTransporte.class,id);
    }
}
