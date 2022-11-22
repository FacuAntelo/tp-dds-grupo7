package repositories;

import models.Miembro.Miembro;
import models.Sector.Sector;
import models.db.EntityManagerHelper;

import java.util.Arrays;
import java.util.List;

public class RepositorioSector {

    public void guardar(Sector... sectores){
        EntityManagerHelper.beginTransaction();
        List<Sector> sectorList = Arrays.asList(sectores);
        sectorList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public Sector buscar(Integer id){
        return EntityManagerHelper.getEntityManager().find(Sector.class,id);
    }


}
