package db;

import unidad.GR;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHelper {
    private static  EntityManagerHelper instancia = null;
    private static EntityManager instanciaEnitityManager = null;

    public static EntityManager getEntityManager() {
        if(instanciaEnitityManager == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("db"); //ver en xml
            instanciaEnitityManager = emf.createEntityManager();
        }
        return instanciaEnitityManager;
    }

    public static EntityManagerHelper getEntityManagerHelper(){
        if (instancia==null){
            instancia = new EntityManagerHelper();
        }
        return  instancia;
    }

}
