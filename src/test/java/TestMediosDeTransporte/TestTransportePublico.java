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
import repositories.RepositorioLocalidad;
import repositories.RepositorioMedioDeTransporte;
import repositories.RepositorioProvincia;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTransportePublico {
    RepositorioLocalidad repositorioLocalidad= new RepositorioLocalidad();
    RepositorioProvincia repositorioProvincia = new RepositorioProvincia();
    RepositorioMedioDeTransporte repositorioMedioDeTransporte = new RepositorioMedioDeTransporte();
    Localidad localidad = repositorioLocalidad.buscarPorId(Long.parseLong("6021010000"));
    Provincia provincia=repositorioProvincia.buscarPorId(6);

    Direccion direccion1 = new Direccion("mitre", 10,localidad, provincia);
    Direccion direccion2 = new Direccion("belgrano", 350,localidad,provincia);
    Direccion direccion3 = new Direccion("guemes", 350,localidad, provincia);
    Direccion direccion4 = new Direccion("nacho", 350,localidad, provincia);
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
        repositorioMedioDeTransporte.guardar(unTransportePublico);
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
    @Test
    public void persistirTransportesPublicos(){
        persistirSubte();
        persistirTren();
        persistirColectivo();
    }

    public void persistirSubte(){
        Linea lineaC = new Linea("C");
        lineaC.setParada(new Parada("C1",direccion1, 2.0,0.0));
        lineaC.setParada(new Parada("C2",direccion2, 4.0,0.0));
        lineaC.setParada(new Parada("C3",direccion3, 6.0,0.0));
        lineaC.setParada(new Parada("C4",direccion4, 0.0,0.0));

        Linea lineaB = new Linea("B");
        lineaB.setParada(new Parada("B1",direccion1, 2.0,0.0));
        lineaB.setParada(new Parada("B2",direccion2, 4.0,0.0));
        lineaB.setParada(new Parada("B3",direccion3, 6.0,0.0));
        lineaB.setParada(new Parada("B4",direccion4, 0.0,0.0));

        repositorioMedioDeTransporte.guardar(new TransportePublico(TipoTransportePublico.SUBTE,lineaC),new TransportePublico(TipoTransportePublico.SUBTE,lineaB));
    }

    public void persistirTren(){
        Linea lineaRoca = new Linea("ROCA");
        lineaRoca.setParada(new Parada("ROCA1",direccion1, 2.0,0.0));
        lineaRoca.setParada(new Parada("ROCA2",direccion2, 4.0,0.0));
        lineaRoca.setParada(new Parada("ROCA3",direccion3, 6.0,0.0));
        lineaRoca.setParada(new Parada("ROCA4",direccion4, 0.0,0.0));

        Linea lineaMitre = new Linea("MITRE");
        lineaMitre.setParada(new Parada("MITRE1",direccion1, 2.0,0.0));
        lineaMitre.setParada(new Parada("MITRE2",direccion2, 4.0,0.0));
        lineaMitre.setParada(new Parada("MITRE3",direccion3, 6.0,0.0));
        lineaMitre.setParada(new Parada("MITRE4",direccion4, 0.0,0.0));

        repositorioMedioDeTransporte.guardar(new TransportePublico(TipoTransportePublico.TREN,lineaRoca),new TransportePublico(TipoTransportePublico.TREN,lineaMitre));
    }

    public void persistirColectivo(){
        Linea linea7 = new Linea("7");
        linea7.setParada(new Parada("71",direccion1, 2.0,0.0));
        linea7.setParada(new Parada("72",direccion2, 4.0,0.0));
        linea7.setParada(new Parada("73",direccion3, 6.0,0.0));
        linea7.setParada(new Parada("74",direccion4, 0.0,0.0));

        Linea linea101 = new Linea("101");
        linea101.setParada(new Parada("1011",direccion1, 2.0,0.0));
        linea101.setParada(new Parada("1012",direccion2, 4.0,0.0));
        linea101.setParada(new Parada("1013",direccion3, 6.0,0.0));
        linea101.setParada(new Parada("1014",direccion4, 0.0,0.0));

        repositorioMedioDeTransporte.guardar(new TransportePublico(TipoTransportePublico.SUBTE,linea7),new TransportePublico(TipoTransportePublico.COLECTIVO,linea101));
    }



}
