import ServicioGeoRefAPIgob.ServicioGeoRefAPI;
import ServicioGeoRefAPIgob.retrofit.ServicioGeoRefAPIRetrofit;
import models.trayecto.Localidad;
import models.trayecto.Provincia;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestGeoRefAPI {

    @Test
    public void testAPI() throws IOException {
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
    }
}