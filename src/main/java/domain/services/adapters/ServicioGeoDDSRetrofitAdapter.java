package domain.services.adapters;

import domain.services.ServicioGeoDDS;
import domain.services.entities.DistanciaAPI;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioGeoDDSRetrofitAdapter implements GeoDDSAdapter {
    private static ServicioGeoDDS instancia= null;
    private Retrofit retrofit;
    private static final String urlAPI="https://ddstpa.com.ar/api/";
    public ServicioGeoDDSRetrofitAdapter(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public DistanciaAPI distanciaAPI(int locOr, String calleOr, int altOr, int locDes, String calleDes, int altDes) throws IOException {
        GeoDDSInterface geoDDSInterface = this.retrofit.create(GeoDDSInterface.class);
        Call<DistanciaAPI> requestDistancia = geoDDSInterface.distancia(locOr,calleOr,altOr,locDes,calleDes,locDes);
        Response<DistanciaAPI> responseDistancia = requestDistancia.execute();
        return responseDistancia.body();
    }
}