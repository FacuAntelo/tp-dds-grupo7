package repositories;

import models.Miembro.Miembro;
import models.Organizacion.Organizacion;
import models.Sector.Sector;
import models.db.EntityManagerHelper;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioOrganizacion {

    public void guardar(Organizacion ... organizacion){
        EntityManagerHelper.beginTransaction();
        List<Organizacion> organizacionList = Arrays.asList(organizacion);
        organizacionList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public void eliminar(Organizacion... organizacion){
        EntityManagerHelper.beginTransaction();
        List<Organizacion> organizacionList = Arrays.asList(organizacion);
        organizacionList.forEach(EntityManagerHelper.entityManager()::remove);
        EntityManagerHelper.commit();
    }

    public void actualizar(Organizacion... organizacion){
        EntityManagerHelper.beginTransaction();
        List<Organizacion> organizacionList = Arrays.asList(organizacion);
        organizacionList.forEach(EntityManagerHelper.entityManager()::refresh);
        EntityManagerHelper.commit();
    }

    public List<Organizacion> buscarTodos(){
        return (List<Organizacion>) EntityManagerHelper.getEntityManager().createQuery("from Organizacion",Organizacion.class).getResultList();
    }

    public Organizacion buscar(int id){
        return EntityManagerHelper.getEntityManager().find(Organizacion.class,id);
    }

    public List<Miembro> buscarTodosLosMiembros(Integer idOrganizacion){
        List<Sector> sectorList = EntityManagerHelper.getEntityManager().
                createQuery("select s from Organizacion as o inner join o.sectores as s where o.id = :idOrganizacion", Sector.class).
                setParameter("idOrganizacion", idOrganizacion).getResultList();
        return sectorList.stream().flatMap(s -> s.getMiembros().stream()).collect(Collectors.toList());
    }

    public List<Sector> buscarTodosLosSectores(Integer idOrganizacion){
        return EntityManagerHelper.getEntityManager().
                createQuery("select s from Organizacion as o inner join o.sectores as s where o.id = :idOrganizacion", Sector.class).
                setParameter("idOrganizacion", idOrganizacion).getResultList();
    }
}