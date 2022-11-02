package ServicioGeoRefAPIgob.interfaces;

import ServicioGeoRefAPIgob.plantillas.ListadoDeProvincias;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProvinciaInterface {
    @GET("provincias")
    Call<ListadoDeProvincias> getProvincias();
}
