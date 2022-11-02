package repositories;

import models.Organizacion.DatosDeActividad;
import models.Organizacion.Organizacion;
import models.Usuarios.FactorDeEmision;
import models.db.EntityManagerHelper;

import java.util.List;

public class RepositorioFactoresDeEmision {

    public List<FactorDeEmision> buscarTodos(){
        return EntityManagerHelper.getEntityManager().createQuery("from FactorDeEmision",FactorDeEmision.class).getResultList();
    }

}
