package repositories;

import models.db.EntityManagerHelper;
import models.trayecto.Localidad;

import java.util.List;

public class RepositorioLocalidad {
    public void guardarLocalidades(List<Localidad> localidadList){
        EntityManagerHelper.beginTransaction();
        localidadList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }
}
