package repositories;

import models.Organizacion.Organizacion;
import models.db.EntityManagerHelper;
import models.trayecto.Direccion;

import java.util.Arrays;
import java.util.List;

public class RepositorioDireccion {
    public void guardar(Direccion... direcciones){
        EntityManagerHelper.beginTransaction();
        List<Direccion> direccionList = Arrays.asList(direcciones);
        direccionList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }
    public Direccion buscar(Integer id){
        return EntityManagerHelper.getEntityManager().find(Direccion.class,id);
    }
}
