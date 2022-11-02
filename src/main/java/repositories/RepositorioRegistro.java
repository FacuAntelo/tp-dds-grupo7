package repositories;

import models.HuellaDeCarbono.RegistroHC;
import models.db.EntityManagerHelper;

import java.util.List;

public class RepositorioRegistro {

    public List<RegistroHC> getRegistroHCListDeOrganizacion(int id){
        List<RegistroHC> registroHCList = EntityManagerHelper.getEntityManager().createQuery("" +
                "select r from Organizacion as o join o.registrosHC as r" +
                "where o.id="+ id,RegistroHC.class).getResultList();
        return registroHCList;
    }
}
