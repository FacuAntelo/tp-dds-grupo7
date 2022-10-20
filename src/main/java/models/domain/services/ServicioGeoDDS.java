package models.domain.services;

import models.domain.services.adapters.GeoDDSAdapter;
import models.domain.services.entities.DistanciaAPI;

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
