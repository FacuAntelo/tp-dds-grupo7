package TestMediosDeTransporte;
import MediosDeTransporte.Linea;
import MediosDeTransporte.Parada;
import MediosDeTransporte.TipoTransportePublico;
import MediosDeTransporte.TransportePublico;
import org.junit.Before;
import org.junit.Test;
import trayecto.Calle;
import trayecto.Direccion;
import trayecto.Localidad;
import trayecto.Provincia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTransportePublico {
    Direccion direccion1 = new Direccion(new Calle("mitre"), 10, new Localidad(100), new Provincia("BsAs"));
    Direccion direccion2 = new Direccion(new Calle("belgrano"), 350, new Localidad(10), new Provincia("BsAs"));
    Direccion direccion3 = new Direccion(new Calle("guemes"), 350, new Localidad(10), new Provincia("BsAs"));
    Direccion direccion4 = new Direccion(new Calle("nacho"), 350, new Localidad(10), new Provincia("BsAs"));
    TransportePublico unTransportePublico;
    Parada unaParada= new MediosDeTransporte.Parada("2",direccion1, 2.0,0.0);
    Parada otraParada= new MediosDeTransporte.Parada("4",direccion1, 6.0,0.0);

    @Before
    public void inicializacion(){
        TipoTransportePublico tipoTp= TipoTransportePublico.COLECTIVO;

        Linea linea = new Linea("1");
        linea.setParada(new MediosDeTransporte.Parada("1",direccion1, 2.0,0.0));
        linea.setParada(new MediosDeTransporte.Parada("2",direccion2, 4.0,0.0));
        linea.setParada(new MediosDeTransporte.Parada("3",direccion3, 6.0,0.0));
        linea.setParada(new MediosDeTransporte.Parada("4",direccion4, 0.0,0.0));

        unTransportePublico = new TransportePublico(tipoTp,linea);
    }
    @Test
    public void calculoDeDistanciaEntreDosParadas(){
        inicializacion();
        System.out.print(unTransportePublico.calcularDistanciaEntreParadas(unaParada,otraParada));
        System.out.print(unTransportePublico.calcularDistanciaEntreParadas(otraParada,unaParada));

        assertEquals(10, unTransportePublico.calcularDistanciaEntreParadas(otraParada,unaParada),0);

    }
    @Test
    public void calcularDistanciaEntreDosParadasDesdeDistanciaAPPI(){
        inicializacion();
        assertEquals(10,unTransportePublico.distancia(direccion2, direccion4).getValor(),0);

    }



}
