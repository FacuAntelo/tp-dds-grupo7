package repositories;

import models.Organizacion.DatosDeActividad;
import models.Organizacion.Organizacion;
import models.Sector.Sector;
import models.Usuarios.FactorDeEmision;
import models.db.EntityManagerHelper;

import java.util.Arrays;
import java.util.List;

public class RepositorioFactoresDeEmision {
    public void guardar(FactorDeEmision ... factorDeEmisions){
        EntityManagerHelper.beginTransaction();
        List<FactorDeEmision> factorDeEmisionList = Arrays.asList(factorDeEmisions);
        factorDeEmisionList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public List<FactorDeEmision> buscarTodos(){
        return EntityManagerHelper.getEntityManager().createQuery("from FactorDeEmision",FactorDeEmision.class).getResultList();
    }

    public FactorDeEmision buscar(Integer id){
        return EntityManagerHelper.getEntityManager().find(FactorDeEmision.class,id);
    }

    public FactorDeEmision buscarPorNombre(String nombre){

        return EntityManagerHelper.getEntityManager().
                createQuery("select f from FactorDeEmision as f where f.nombre = :nombreFE", FactorDeEmision.class).
                setParameter("nombreFE", nombre).getSingleResult();
    }

}