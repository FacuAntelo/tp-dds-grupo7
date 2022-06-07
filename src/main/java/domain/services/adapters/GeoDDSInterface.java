package domain.services.adapters;

import domain.services.entities.DistanciaAPI;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GeoDDSInterface {
    @Headers("Authorization: Bearer E4QhrgzHOD+rpwKdreaA62TTIOKGciQOOmAIgtrr2z8=")
    @GET("distancia")
    Call<DistanciaAPI> distancia(@Query("localidadOrigenId") int localidadOrigenId, @Query("calleOrigen") String calleOrigen, @Query("alturaOrigen") int alturaOrigen,
                                 @Query("localidadDestinoId") int localidadDestinold, @Query("calleDestino") String calleDestino, @Query("alturaDestino") int alturaDestino);
}
