package repositories;

import ServicioGeoRefAPIgob.ServicioGeoRefAPI;
import ServicioGeoRefAPIgob.retrofit.ServicioGeoRefAPIRetrofit;
import models.db.EntityManagerHelper;
import models.trayecto.Localidad;
import models.trayecto.Provincia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioProvincia {
    public void persistirTodas(List<Provincia> provincias){
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        provincias.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
    public List<Provincia> traerTodas(){
        return EntityManagerHelper.getEntityManager().createQuery("select p from Provincia as p",Provincia.class).getResultList();
    }

    public List<Provincia> cargarProvincias() throws IOException {

        System.out.println("EMPEZANDO LA CARGA DE PROVINCIAS");

        ServicioGeoRefAPI servicioGeoRefAPI = new ServicioGeoRefAPI();
        servicioGeoRefAPI.setAdapter(new ServicioGeoRefAPIRetrofit());

        List<Provincia> provinciaAPIList = servicioGeoRefAPI.getProvincias();
        provinciaAPIList.forEach(p -> {
            List<Localidad> localidadAPIList = new ArrayList<>();
            try {
                localidadAPIList = servicioGeoRefAPI.getLocalidadesPorProvincia(p.getNombre());
            } catch (IOException e) {
                e.printStackTrace();
            }
            p.setLocalidades(localidadAPIList);
        });
        provinciaAPIList.forEach(p -> {
            System.out.println("--------------------------------------------------------------------");
            System.out.println(p.getNombre() + " " + p.getId());
            p.getLocalidades().forEach(l -> System.out.println(l.getNombre() + " " + l.getId()));
        });
        System.out.println("TERMINO LA CARGA");
        return provinciaAPIList;
    }
}
