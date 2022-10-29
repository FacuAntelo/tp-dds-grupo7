package repositories;

import models.Organizacion.EstadoPeticion;
import models.Organizacion.Organizacion;
import models.Organizacion.Peticion;
import models.Sector.Sector;
import models.db.EntityManagerHelper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioPeticion {
    public void guardar(Peticion... peticion){
        EntityManagerHelper.beginTransaction();
        List<Peticion> peticionList = Arrays.asList(peticion);
        peticionList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }
    public void actualizar(Peticion... peticion){
        EntityManagerHelper.beginTransaction();
        List<Peticion> peticionList = Arrays.asList(peticion);
        peticionList.forEach(EntityManagerHelper.entityManager()::merge);
        EntityManagerHelper.commit();
    }
    public void eliminar(Peticion... peticion){
        EntityManagerHelper.beginTransaction();
        List<Peticion> peticionList = Arrays.asList(peticion);
        peticionList.forEach(EntityManagerHelper.entityManager()::remove);
        EntityManagerHelper.commit();
    }

    public List<Peticion> buscarTodos(int idOrganizacion){
        List<Peticion> peticionList = EntityManagerHelper.getEntityManager().createQuery("from Peticion",Peticion.class).getResultList();
        peticionList = peticionList.stream().filter(x -> x.getOrganizacion().getId()==idOrganizacion).collect(Collectors.toList());

        return peticionList;
    }

    public List<Peticion> buscarPendientes(int idOrganizacion){
        List<Peticion> peticionList = EntityManagerHelper.getEntityManager().createQuery("from Peticion",Peticion.class).getResultList();
        peticionList = peticionList.stream().filter(x -> x.getOrganizacion().getId()==idOrganizacion && x.getEstadoPeticion()== EstadoPeticion.PENDIENTE).collect(Collectors.toList());

        return peticionList;
    }

    public Peticion findByID(int id){
        return EntityManagerHelper.getEntityManager().find(Peticion.class,id);
    }

//    public void rechazarPeticionesPendientesDelUsuarioDeLaOrganizacion(int idUsuario, int idOrganizacion) {
//        List<Peticion> peticionesABorrar = buscarTodos(idOrganizacion)
//                .stream()
//                .filter(p-> p.getEstadoPeticion()==EstadoPeticion.PENDIENTE && p.getUsuario().getId()==idUsuario)
//                .collect(Collectors.toList());
//
//        actualizar((Peticion) peticionesABorrar);
//
//    }
}
