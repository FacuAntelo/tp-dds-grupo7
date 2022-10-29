package repositories;

        import models.DTO.TramoDTO;
        import models.DTO.TrayectoDTO;
        import models.Miembro.Miembro;
        import models.Organizacion.Organizacion;
        import models.db.EntityManagerHelper;
        import models.trayecto.Tramo;
        import models.trayecto.Trayecto;

        import javax.persistence.EntityManager;
        import java.util.ArrayList;
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

    public Miembro buscarOrganizacion(Integer idOrganizacion,Integer idUsuario){
        List<Miembro> miembroList = EntityManagerHelper.getEntityManager()
                .createQuery("select m from Organizacion as o " +
                        "inner join o.sectores as s " +
                        "inner join s.miembros as m " +
                        "where o.id = :id", Miembro.class)
                .setParameter("id", idOrganizacion).getResultList();
        miembroList = miembroList.stream().filter(m-> m.getUsuario().getId()==idUsuario).collect(Collectors.toList());
    if(miembroList.isEmpty()){
        return null;
    } else{
        return  miembroList.get(0);
    }
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

    public List<TrayectoDTO>buscarTrayectos(Miembro miembro){
        List<Trayecto> trayectos = this.buscar(miembro.getId()).getTrayectos();
        List<TrayectoDTO> trayectoDTOList= new ArrayList<>();

        trayectos.forEach(t ->{
            TrayectoDTO trayectoDTO= new TrayectoDTO();
            trayectoDTO.setId(t.getId());
            trayectoDTO.setDistanciaTotal(t.getDistanciaTotal());
            trayectoDTO.setDireccionInicioCalle(t.getPuntoInicio().getCalle());
            trayectoDTO.setDireccionInicioAltura(t.getPuntoInicio().getAltura());
            trayectoDTO.setDireccionFinLocalidad(Integer.toString(t.getPuntoInicio().getLocalidad().getNumeroLocalidad()));
            trayectoDTO.setDireccionInicioProvincia(t.getPuntoInicio().getProvincia().getProvincia());
            trayectoDTO.setDireccionFinCalle(t.getPuntoFin().getCalle());
            trayectoDTO.setDireccionFinAltura(t.getPuntoFin().getAltura());
            trayectoDTO.setDireccionFinLocalidad(Integer.toString(t.getPuntoFin().getLocalidad().getNumeroLocalidad()));
            trayectoDTO.setDireccionFinProvincia(t.getPuntoFin().getProvincia().getProvincia());
            trayectoDTOList.add(trayectoDTO);
        });

        return trayectoDTOList;
    }

    public List<TramoDTO>buscarTramos(Miembro miembro, int idTrayecto){
        List<Tramo> tramos = this.buscar(miembro.getId()).getTramos();
        List<TramoDTO> tramoDTOList= new ArrayList<>();

        tramos.forEach(t ->{
            TramoDTO tramoDTO= new TramoDTO();
            tramoDTO.setId(t.getId());
            tramoDTO.setTipoTransporte(t.getMedioDeTransporte().getTipoTransporte());
            tramoDTO.setHoraInicio(t.getHoraInicio());
            tramoDTO.setDireccionInicioCalle(t.getUbicacionInicio().getCalle());
            tramoDTO.setDireccionInicioAltura(t.getUbicacionInicio().getAltura());
            tramoDTO.setDireccionFinLocalidad(Integer.toString(t.getUbicacionInicio().getLocalidad().getNumeroLocalidad()));
            tramoDTO.setDireccionInicioProvincia(t.getUbicacionInicio().getProvincia().getProvincia());
            tramoDTO.setDireccionFinCalle(t.getUbicacionFinal().getCalle());
            tramoDTO.setDireccionFinAltura(t.getUbicacionFinal().getAltura());
            tramoDTO.setDireccionFinLocalidad(Integer.toString(t.getUbicacionFinal().getLocalidad().getNumeroLocalidad()));
            tramoDTO.setDireccionFinProvincia(t.getUbicacionFinal().getProvincia().getProvincia());
            tramoDTOList.add(tramoDTO);
        });

        return tramoDTOList;
    }

    public Miembro buscarUsuario(int idUsuario){
        List<Miembro> miembroList = EntityManagerHelper.getEntityManager().createQuery("" +
                "select o from Miembro as o", Miembro.class).getResultList();
        miembroList = miembroList.stream().filter(m-> m.getUsuario().getId() == idUsuario).collect(Collectors.toList());

        return miembroList.get(0);
    }

}