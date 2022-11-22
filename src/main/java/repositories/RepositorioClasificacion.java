package repositories;

import models.Organizacion.Clasificacion;
import models.db.EntityManagerHelper;

import java.util.List;

public class RepositorioClasificacion {

    public List<Clasificacion> buscarTodas() {
        return EntityManagerHelper.getEntityManager().createQuery("from Clasificacion", Clasificacion.class).getResultList();
    }

    public Clasificacion buscar(int id){
        return EntityManagerHelper.getEntityManager().find(Clasificacion.class,id);
    }
}
