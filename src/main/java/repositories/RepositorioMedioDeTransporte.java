package repositories;

import models.Combustible.Combustible;
import models.DTO.ServicioContratadoDTO;
import models.MediosDeTransporte.*;
import models.Miembro.Miembro;
import models.db.EntityManagerHelper;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioMedioDeTransporte {
    public void guardar(MediosDeTransporte... mediosDeTransportes){
        EntityManagerHelper.beginTransaction();
        List<MediosDeTransporte> mediosDeTransportesList = Arrays.asList(mediosDeTransportes);
        mediosDeTransportesList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public void eliminar(MediosDeTransporte... mediosDeTransportes){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        List<MediosDeTransporte> mediosDeTransporteList = Arrays.asList(mediosDeTransportes);
        mediosDeTransporteList.forEach(entityManager::remove);
        entityManager.getTransaction().commit();
    }

    public void actualizar(MediosDeTransporte... mediosDeTransportes){
        EntityManager entityManager = EntityManagerHelper.getEntityManager();
        entityManager.getTransaction().begin();
        List<MediosDeTransporte> mediosDeTransportesList = Arrays.asList(mediosDeTransportes);
        mediosDeTransportesList.forEach(entityManager::refresh);
        entityManager.getTransaction().commit();
    }



    public MediosDeTransporte buscar(int id){
        return EntityManagerHelper.getEntityManager().find(MediosDeTransporte.class,id);
    }

    public List<VehiculoParticular> obtenerTodosLosVehiculos(){
        return EntityManagerHelper.getEntityManager()
                .createQuery("from VehiculoParticular",VehiculoParticular.class).getResultList();
    }
    public List<MediosSinContaminar> obtenerTodosLosMediosSinContaminar(){
        return EntityManagerHelper.getEntityManager()
                .createQuery("from MediosSinContaminar", MediosSinContaminar.class).getResultList();
    }

    public List<ServicioContratadoDTO> obtenerTodosLosServiciosContratadosDTO(){
        List<ServicioContratadoDTO> servicioContratadoDTOList= new ArrayList<>();
        List<ServicioContratado> servicioContratadoList = EntityManagerHelper.getEntityManager()
                .createQuery("from ServicioContratado", ServicioContratado.class).getResultList();

        servicioContratadoList.forEach(s-> {
            servicioContratadoDTOList.add(new ServicioContratadoDTO(s.getId(),s.getServicio().getNombre()));
        });

        return servicioContratadoDTOList;
    }

    public VehiculoParticular obtenerMedioDeTransporte(TipoVehiculo tipoVehiculo, Combustible combustible, boolean esCompartido){
        List<VehiculoParticular> vehiculoParticularList= EntityManagerHelper.getEntityManager()
                .createQuery("from VehiculoParticular",VehiculoParticular.class)
                .getResultList()
                .stream().filter(v-> v.getTipo()==tipoVehiculo && v.getCombustible()==combustible && v.getEsCompartido()==esCompartido).collect(Collectors.toList());

        if(vehiculoParticularList.isEmpty()){
            VehiculoParticular vehiculoParticular = new VehiculoParticular(tipoVehiculo,combustible,esCompartido);
            this.guardar(vehiculoParticular);
            return vehiculoParticular;
        }
        else {
            return vehiculoParticularList.get(0);
        }
    }

    public MediosSinContaminar obtenerMedioDeTransporte(String nombre, boolean esCompartido){
        List<MediosSinContaminar>  mediosSinContaminarList=EntityManagerHelper.getEntityManager()
                .createQuery("from MediosSinContaminar",MediosSinContaminar.class)
                .getResultList()
                .stream().filter(m-> m.getNombre().equals(nombre) && m.getEsCompartido()==esCompartido).collect(Collectors.toList());

        if(mediosSinContaminarList.isEmpty()){
            MediosSinContaminar mediosSinContaminar = new MediosSinContaminar(nombre,esCompartido);
            this.guardar(mediosSinContaminar);
            return mediosSinContaminar;
        }
        else {
            return mediosSinContaminarList.get(0);
        }

    }


    public ServicioContratado obtenerMedioDeTransporte(Servicio servicio, Combustible combustible, Boolean esCompartido) {

        List<ServicioContratado>  servicioContratadoList=EntityManagerHelper.getEntityManager()
                .createQuery("from ServicioContratado",ServicioContratado.class)
                .getResultList()
                .stream().filter(s-> s.getServicio()==servicio && s.getCombustible()==combustible &&s.getEsCompartido()==esCompartido).collect(Collectors.toList());

        if(servicioContratadoList.isEmpty()){
            ServicioContratado servicioContratado = new ServicioContratado(servicio,combustible,esCompartido);
            this.guardar(servicioContratado);
            return servicioContratado;
        }
        else {
            return servicioContratadoList.get(0);
        }
    }
}
