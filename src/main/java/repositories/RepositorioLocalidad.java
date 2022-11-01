package repositories;

import models.db.EntityManagerHelper;
import models.trayecto.Localidad;
import models.trayecto.Provincia;

import java.util.List;

public class RepositorioLocalidad {
    public void guardarLocalidades(List<Localidad> localidadList){
        EntityManagerHelper.beginTransaction();
        localidadList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public List<Localidad> buscarPorIdProvincia(int idProvincia){
        List<Localidad> localidadList = EntityManagerHelper.getEntityManager().createQuery("select l from Provincia p join p.localidades as l where p.id = :idProvincia",Localidad.class)
                .setParameter("idProvincia",idProvincia)
                .getResultList();
        if(localidadList.isEmpty()){
            System.out.println("ee");
        }
        System.out.println(localidadList.size());
        return localidadList;
    }

    public Localidad buscarPorId(long id){
        return EntityManagerHelper.getEntityManager().createQuery("select l from Localidad l where l.id="+id,Localidad.class).getSingleResult();

    }
}
