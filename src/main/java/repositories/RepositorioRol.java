package repositories;

import models.Organizacion.Organizacion;
import models.Usuarios.Rol;
import models.db.EntityManagerHelper;

import java.util.Arrays;
import java.util.List;

public class RepositorioRol {
    public void guardar(Rol... roles){
        EntityManagerHelper.beginTransaction();
        List<Rol> rolList = Arrays.asList(roles);
        rolList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public List<Rol> buscarTodos(){
        return EntityManagerHelper.getEntityManager().createQuery("from Rol",Rol.class).getResultList();
    }
    public Rol buscar(Integer id){
        return EntityManagerHelper.getEntityManager().find(Rol.class,id);
    }
}
