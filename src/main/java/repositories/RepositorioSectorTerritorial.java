package repositories;

import models.AgenteSectorial.SectorTerritorial;
import models.Usuarios.FactorDeEmision;
import models.db.EntityManagerHelper;

import java.util.Arrays;
import java.util.List;

public class RepositorioSectorTerritorial {
    public void guardar(SectorTerritorial... sectoresTerritoriales){
        EntityManagerHelper.beginTransaction();
        List<SectorTerritorial> sectorTerritorialList = Arrays.asList(sectoresTerritoriales);
        sectorTerritorialList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public List<SectorTerritorial> buscarTodos(){
        return EntityManagerHelper.getEntityManager().createQuery("from SectorTerritorial",SectorTerritorial.class).getResultList();
    }

    public SectorTerritorial buscar(Integer id){
        return EntityManagerHelper.getEntityManager().find(SectorTerritorial.class,id);
    }
}
