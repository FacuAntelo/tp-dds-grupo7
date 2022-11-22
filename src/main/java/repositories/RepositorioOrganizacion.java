package repositories;

import models.DTO.MiembroOrganizacionDTO;
import models.Miembro.Miembro;
import models.Organizacion.Organizacion;
import models.Sector.Sector;
import models.db.EntityManagerHelper;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioOrganizacion {
    RepositorioMiembro repositorioMiembro= new RepositorioMiembro();

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

    public Organizacion buscar(Integer id){
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

    public List<Organizacion> buscarOrganizacionesDelUsuarioQueEsMiembro(Integer idUsuario) {
        List<Organizacion> organizacionList = EntityManagerHelper.getEntityManager().createQuery("" +
                "select o from Organizacion as o", Organizacion.class).getResultList();
        organizacionList = organizacionList.stream().filter(
                o -> o.getSectores().stream().flatMap(s -> s.getMiembros().stream())
                        .anyMatch(m -> m.getUsuario().getId() == idUsuario)).collect(Collectors.toList());
        return organizacionList;
    }

    public List<Organizacion> buscarOrganizacionesDelUsuarioQueEsAdministrador(Integer idUsuario){

        return EntityManagerHelper.getEntityManager().createQuery("select o from Organizacion o" +
                " where o.usuario = " + idUsuario, Organizacion.class).getResultList();
    }
    public List<Organizacion> buscarOrganizacionesDelUsuarioQueEsMiembroOAdministrador(Integer idUsuario){
        List<Organizacion> organizacionList = buscarOrganizacionesDelUsuarioQueEsMiembro(idUsuario);
        organizacionList.addAll(buscarOrganizacionesDelUsuarioQueEsAdministrador(idUsuario));

        return organizacionList;
    }


//    public MiembroOrganizacionDTO buscarOrganizacionMiembroDTO(int idUsuario){
//        List<Organizacion> organizacionList = buscarOrganizacionesDeUsuario(Integer.valueOf(idUsuario));
//        Miembro miembro = repositorioMiembro.buscarUsuario(idUsuario);
//        MiembroOrganizacionDTO dto = new MiembroOrganizacionDTO();
//        dto.setIdMiembro(miembro.getId());
//        dto.setIdOrganizacion();
//
//
//    }

    public Sector buscarSector(int idOrganizacion, int idSector){
        return buscarTodosLosSectores(Integer.valueOf(idOrganizacion)).stream().filter(x->x.getId()==idSector).collect(Collectors.toList()).get(0);

    }
    public void guardarSectores(Sector ... sectores){
        EntityManagerHelper.beginTransaction();
        List<Sector> sectorList = Arrays.asList(sectores);
        sectorList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }
}
