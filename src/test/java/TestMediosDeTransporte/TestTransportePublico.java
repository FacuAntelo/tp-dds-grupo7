package TestMediosDeTransporte;
import models.MediosDeTransporte.Linea;
import models.MediosDeTransporte.Parada;
import models.MediosDeTransporte.TipoTransportePublico;
import models.MediosDeTransporte.TransportePublico;
import org.junit.Before;
import org.junit.Test;
import models.trayecto.Direccion;
import models.trayecto.Localidad;
import models.trayecto.Provincia;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTransportePublico {
    Direccion direccion1 = new Direccion("mitre", 10, new Localidad(), new Provincia("BsAs"));
    Direccion direccion2 = new Direccion("belgrano", 350, new Localidad(), new Provincia("BsAs"));
    Direccion direccion3 = new Direccion("guemes", 350, new Localidad(), new Provincia("BsAs"));
    Direccion direccion4 = new Direccion("nacho", 350, new Localidad(), new Provincia("BsAs"));
    TransportePublico unTransportePublico;
    Parada unaParada= new Parada("2",direccion1, 2.0,0.0);
    Parada otraParada= new Parada("4",direccion1, 6.0,0.0);

    @Before
    public void inicializacion() throws IOException {
        TipoTransportePublico tipoTp= TipoTransportePublico.COLECTIVO;

        Linea linea = new Linea("1");
        linea.setParada(new Parada("1",direccion1, 2.0,0.0));
        linea.setParada(new Parada("2",direccion2, 4.0,0.0));
        linea.setParada(new Parada("3",direccion3, 6.0,0.0));
        linea.setParada(new Parada("4",direccion4, 0.0,0.0));

        unTransportePublico = new TransportePublico(tipoTp,linea);
    }
    @Test
    public void calculoDeDistanciaEntreDosParadas() throws IOException {
        inicializacion();
        System.out.print(unTransportePublico.calcularDistanciaEntreParadas(unaParada,otraParada));
        System.out.print(unTransportePublico.calcularDistanciaEntreParadas(otraParada,unaParada));

        assertEquals(10, unTransportePublico.calcularDistanciaEntreParadas(otraParada,unaParada),0);

    }
    @Test
    public void calcularDistanciaEntreDosParadasDesdeDistanciaAPPI() throws IOException {
        inicializacion();
        assertEquals(10,unTransportePublico.distancia(direccion2, direccion4).getValor(),0);

    }



}
