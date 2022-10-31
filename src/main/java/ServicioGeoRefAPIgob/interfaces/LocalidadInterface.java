package ServicioGeoRefAPIgob.interfaces;

import ServicioGeoRefAPIgob.plantillas.ListadoDeLocalidades;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocalidadInterface {
    @GET("localidades")
    Call<ListadoDeLocalidades> getLocalidadesDeProvincia(@Query("provincia") String nombreProvincia, @Query("max") long max);
}
