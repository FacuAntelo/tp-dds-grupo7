package TestMediosDeTransporte;
import MediosDeTransporte.Linea;
import MediosDeTransporte.Parada;
import MediosDeTransporte.TipoTransportePublico;
import MediosDeTransporte.TransportePublico;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import trayecto.Calle;
import trayecto.Direccion;
import trayecto.Localidad;
import trayecto.Provincia;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTransportePublico {
    Direccion direccion = new Direccion(new Calle("mitre"), 10, new Localidad(100), new Provincia("BsAs"));
    TransportePublico unTransportePublico;
    Parada unaParada= new MediosDeTransporte.Parada("2",direccion, 2.0,0.0);
    Parada otraParada= new MediosDeTransporte.Parada("4",direccion, 6.0,0.0);

    @Before
    public void inicializacion(){
        TipoTransportePublico tipoTp= TipoTransportePublico.COLECTIVO;

        Linea linea = new Linea("1");
        linea.agregarParada(new MediosDeTransporte.Parada("1",direccion, 2.0,0.0));
        linea.agregarParada(new MediosDeTransporte.Parada("2",direccion, 4.0,0.0));
        linea.agregarParada(new MediosDeTransporte.Parada("3",direccion, 6.0,0.0));
        linea.agregarParada(new MediosDeTransporte.Parada("4",direccion, 0.0,0.0));

        unTransportePublico = new TransportePublico(tipoTp,linea);
    }
    @Test
    public void calculoDeDistanciaEntreDosParadas(){
        inicializacion();
        System.out.print(unTransportePublico.calcularDistanciaEntreParadas2(unaParada,otraParada));
        System.out.print(unTransportePublico.calcularDistanciaEntreParadas2(otraParada,unaParada));
        //assertTrue( unTransportePublico.calcularDistanciaEntreParadas2(unaParada,otraParada)==10);
        //Assert.assertEquals(10, unTransportePublico.calcularDistanciaEntreParadas2(otraParada,unaParada));

    }



}
