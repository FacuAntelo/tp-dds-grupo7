package domain.services.adapters;

import domain.services.entities.DistanciaAPI;

import java.io.IOException;

public interface GeoDDSAdapter {
    public DistanciaAPI distanciaAPI(int locOr, String calleOr, int altOr, int locDes, String calleDes, int altDes) throws IOException;
}
