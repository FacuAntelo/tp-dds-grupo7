package TestMediosDeTransporte;

import models.Combustible.Combustible;
import models.DTO.ServicioContratadoDTO;
import models.MediosDeTransporte.MediosSinContaminar;
import models.MediosDeTransporte.ServicioContratado;
import models.MediosDeTransporte.TipoVehiculo;
import models.MediosDeTransporte.VehiculoParticular;
import models.Organizacion.Organizacion;
import models.db.EntityManagerHelper;
import org.junit.Test;
import repositories.RepositorioCombustible;
import repositories.RepositorioMedioDeTransporte;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestMediosDeTransporte {
    RepositorioMedioDeTransporte repositorioMedioDeTransporte = new RepositorioMedioDeTransporte();


    @Test
    public void obtenerTodosLosVehiculos(){
        RepositorioCombustible repositorioCombustible = new RepositorioCombustible();
        Combustible combustible= repositorioCombustible.buscarPorId(9);

        List<VehiculoParticular> vehiculoParticularList= EntityManagerHelper.getEntityManager()
                .createQuery("from VehiculoParticular",VehiculoParticular.class)
                .getResultList()
                .stream().filter(v-> v.getTipo()== TipoVehiculo.AUTO && v.getCombustible()==combustible && v.getEsCompartido()==false).collect(Collectors.toList());
        System.out.println(vehiculoParticularList.size());

        vehiculoParticularList.forEach(c-> System.out.println(c.getId()));

    }

    @Test
    public void obtenerMediosSinContaminar(){
        List<MediosSinContaminar>  mediosSinContaminarListTodo=EntityManagerHelper.getEntityManager()
                .createQuery("from MediosSinContaminar",MediosSinContaminar.class)
                .getResultList();
        System.out.println(mediosSinContaminarListTodo.size());
        System.out.println(mediosSinContaminarListTodo.get(0).getNombre().equals("pie"));
        mediosSinContaminarListTodo.forEach(c-> System.out.println(c.getNombre()));

        List<MediosSinContaminar>  mediosSinContaminarList=EntityManagerHelper.getEntityManager()
                .createQuery("from MediosSinContaminar",MediosSinContaminar.class)
                .getResultList()
                .stream().filter(m-> m.getNombre().equals("pie")).collect(Collectors.toList());

        System.out.println(mediosSinContaminarList.size());

    }

    @Test
    public void servicioContratado(){
        List<ServicioContratadoDTO> servicioContratadoDTOList = repositorioMedioDeTransporte.obtenerTodosLosServiciosContratadosDTO();

        System.out.println(servicioContratadoDTOList.size());

    }
}
