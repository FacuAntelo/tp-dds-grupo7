package ServicioGeoRefAPIgob;

import ServicioGeoRefAPIgob.adapters.GeoRefAPI;
import ServicioGeoRefAPIgob.interfaces.LocalidadInterface;
import ServicioGeoRefAPIgob.interfaces.ProvinciaInterface;
import ServicioGeoRefAPIgob.plantillas.ListadoDeLocalidades;
import ServicioGeoRefAPIgob.plantillas.ListadoDeProvincias;
import lombok.Getter;
import lombok.Setter;
import models.domain.services.ServicioGeoDDS;
import models.trayecto.Localidad;
import models.trayecto.Provincia;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Setter
@Getter
public class ServicioGeoRefAPI implements GeoRefAPI {
    private static ServicioGeoRefAPI instancia= null;
    private GeoRefAPI adapter;

    public void setAdapter(GeoRefAPI adapter) {
        this.adapter = adapter;
    }

    public static ServicioGeoRefAPI getInstance(){
        if (instancia== null){
            instancia = new ServicioGeoRefAPI();
        }
        return instancia;
    }


    @Override
    public List<Localidad> getLocalidadesPorProvincia(String nombreProvincia) throws IOException {
        return this.adapter.getLocalidadesPorProvincia(nombreProvincia);
    }

    @Override
    public List<Provincia> getProvincias() throws IOException {
       return this.adapter.getProvincias();
    }
}
