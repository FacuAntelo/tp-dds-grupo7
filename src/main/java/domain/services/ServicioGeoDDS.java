package domain.services;

import domain.services.adapters.GeoDDSAdapter;
import domain.services.entities.DistanciaAPI;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioGeoDDS {
    private static ServicioGeoDDS instancia= null;
    private GeoDDSAdapter adapter;

    public void setAdapter(GeoDDSAdapter adapter) {
        this.adapter = adapter;
    }

    public static ServicioGeoDDS getInstance(){
        if (instancia== null){
            instancia = new ServicioGeoDDS();
        }
        return instancia;
    }

    public DistanciaAPI distanciaAPI(int locOr, String calleOr, int altOr, int locDes, String calleDes, int altDes) throws IOException {
        return this.adapter.distanciaAPI(locOr,calleOr,altOr,locDes,calleDes,altDes);
    }
}
