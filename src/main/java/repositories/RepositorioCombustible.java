package repositories;


import models.Combustible.Combustible;
import models.Organizacion.Organizacion;
import models.db.EntityManagerHelper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioCombustible {

    public void guardar(Combustible ... combustibles){
        EntityManagerHelper.beginTransaction();
        List<Combustible> combustibleList = Arrays.asList(combustibles);
        combustibleList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }
    public Combustible buscarPorId(int id){
        return EntityManagerHelper.getEntityManager().find(Combustible.class,id);
    }
    public List<Combustible> buscarTodos(){
        return EntityManagerHelper.getEntityManager().createQuery("from Combustible",Combustible.class).getResultList();
    }
}
