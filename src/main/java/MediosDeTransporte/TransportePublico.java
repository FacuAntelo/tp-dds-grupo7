package MediosDeTransporte;

import domain.services.entities.DistanciaAPI;
import domain.services.entities.DistanciaAPI;
import trayecto.Direccion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransportePublico extends MediosDeTransporte{
    private TipoTransportePublico tipo;
    private Linea linea;

    public TransportePublico(TipoTransportePublico tipo, Linea linea) {
        this.tipo = tipo;
        this.linea = linea;
    }

    public TipoTransportePublico getTipo() {return tipo;}

    public void setTipo(TipoTransportePublico tipo) {this.tipo = tipo;}

    public Linea getLinea() {return linea;}

    public void setLinea(Linea linea) {this.linea = linea;}


    public double calcularDistanciaEntreParadas(Parada unaParada, Parada otraParada){
        Parada paradaInicio;
        Parada paradaFin;
        double acumDistancia = 0;

        //Genero una lista de longitud 2, para que saber cuál es el incio y fin
        List<Parada> paradas = linea.getParadas().stream().filter(una_parada -> una_parada.getNombre()==unaParada.getNombre() || una_parada.getNombre()==otraParada.getNombre()).collect(Collectors.toList());
        paradaInicio=paradas.stream().findFirst().get();
        paradaFin=paradas.get(1);

        //Recorro lista de las paradas y voy sumando las distancias
        for(int i =0; i < linea.getParadas().size() && linea.getParadas().get(i)!=paradaFin;i++){
            if (linea.getParadas().get(i)==paradaInicio || acumDistancia>0 ){
                acumDistancia+= linea.getParadas().get(i).getDistanciaProximaParada();
            }
        }
        return acumDistancia;
    }

    @Override
    public DistanciaAPI distancia(Direccion direccionInicial, Direccion direccionFinal){
        DistanciaAPI distanciaApi = new DistanciaAPI();
        Parada parada1 = linea.obtenerParadaDeLaDireccion(direccionInicial);
        Parada parada2 = linea.obtenerParadaDeLaDireccion(direccionFinal);

        distanciaApi.setValor(this.calcularDistanciaEntreParadas(parada1,parada2));
        return distanciaApi;
    }

}