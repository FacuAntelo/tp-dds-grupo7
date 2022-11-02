package repositories;

import models.Organizacion.DatosDeActividad;
import models.Organizacion.Organizacion;
import models.db.EntityManagerHelper;

import java.util.Arrays;
import java.util.List;

public class RepositorioDA {

    public List<DatosDeActividad> buscarTodos(){
        return EntityManagerHelper.getEntityManager().createQuery("from DatosDeActividad",DatosDeActividad.class).getResultList();
    }

    public DatosDeActividad buscar(Integer id){
        return EntityManagerHelper.getEntityManager().find(DatosDeActividad.class,id);
    }
}
