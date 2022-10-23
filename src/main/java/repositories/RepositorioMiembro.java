package repositories;

        import models.Miembro.Miembro;
        import models.Organizacion.Organizacion;
        import models.Sector.Sector;
        import models.db.EntityManagerHelper;
        import models.trayecto.Trayecto;

        import javax.persistence.EntityManager;
        import java.util.Arrays;
        import java.util.List;
        import java.util.stream.Collectors;

public class RepositorioMiembro {

    public void guardar(Miembro... miembro){
        EntityManagerHelper.beginTransaction();
        List<Miembro> miembroList = Arrays.asList(miembro);
        miembroList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public void eliminar(Miembro... miembro){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        List<Miembro> miembroList = Arrays.asList(miembro);
        miembroList.forEach(entityManager::remove);
        entityManager.getTransaction().commit();
    }

    public void actualizar(Miembro... miembro){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        List<Miembro> miembroList = Arrays.asList(miembro);
        miembroList.forEach(entityManager::refresh);
        entityManager.getTransaction().commit();
    }



    public Miembro buscar(int id){
        return EntityManagerHelper.getEntityManager().find(Miembro.class,id);
    }

    public Organizacion buscarOrganizacionQuePertenece(Miembro miembro){
        return EntityManagerHelper.getEntityManager()
                .createQuery("select o from Organizacion as o " +
                        "inner join o.sectores as s " +
                        "inner join s.miembros as m " +
                        "where m.id = :id", Organizacion.class)
                .setParameter("id", miembro.getId()).getSingleResult();
    }
}