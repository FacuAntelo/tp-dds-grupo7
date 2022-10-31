package ServicioGeoRefAPIgob.retrofit;

import ServicioGeoRefAPIgob.adapters.GeoRefAPI;
import ServicioGeoRefAPIgob.interfaces.LocalidadInterface;
import ServicioGeoRefAPIgob.interfaces.ProvinciaInterface;
import ServicioGeoRefAPIgob.plantillas.ListadoDeLocalidades;
import ServicioGeoRefAPIgob.plantillas.ListadoDeProvincias;
import models.domain.services.ServicioGeoDDS;
import models.domain.services.adapters.GeoDDSInterface;
import models.domain.services.entities.DistanciaAPI;
import models.trayecto.Localidad;
import models.trayecto.Provincia;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ServicioGeoRefAPIRetrofit implements GeoRefAPI {
    private static ServicioGeoRefAPIRetrofit instancia= null;
    private Retrofit retrofit;
    private static final String urlAPI="https://apis.datos.gob.ar/georef/api/";
    public ServicioGeoRefAPIRetrofit(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    @Override
    public List<Localidad> getLocalidadesPorProvincia(String nombreProvincia) throws IOException {
        LocalidadInterface localidadDDSInterface = this.retrofit.create(LocalidadInterface.class);
        Call<ListadoDeLocalidades> listadoDeLocalidadesAPICall = localidadDDSInterface.getLocalidadesDeProvincia(nombreProvincia,1000);
        Response<ListadoDeLocalidades> listadoDeLocalidadesAPIResponse = listadoDeLocalidadesAPICall.execute();
        return listadoDeLocalidadesAPIResponse.body().localidades;
    }

    @Override
    public List<Provincia> getProvincias() throws IOException {
        ProvinciaInterface provinciaDDSAdapter = this.retrofit.create(ProvinciaInterface.class);
        Call<ListadoDeProvincias> listadoDeProvinciasAPICall = provinciaDDSAdapter.getProvincias();
        Response<ListadoDeProvincias> listadoDeProvinciasAPIResponse = listadoDeProvinciasAPICall.execute();
        return listadoDeProvinciasAPIResponse.body().provincias;
    }
}
