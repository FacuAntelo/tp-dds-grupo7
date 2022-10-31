package ServicioGeoRefAPIgob.adapters;

import models.trayecto.Localidad;
import models.trayecto.Provincia;

import java.io.IOException;
import java.util.List;

public interface GeoRefAPI {
    public List<Localidad> getLocalidadesPorProvincia(String nombreProvincia) throws IOException;

    public List<Provincia> getProvincias() throws IOException;

}
