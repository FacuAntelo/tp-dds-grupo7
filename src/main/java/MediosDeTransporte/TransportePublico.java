package MediosDeTransporte;

import domain.services.entities.DistanciaAPI;
import domain.services.entities.DistanciaAPI;
import trayecto.Direccion;

public class TransportePublico extends MediosDeTransporte{
    private TipoTransportePublico tipo;
    private Linea linea;

    public TransportePublico(TipoTransportePublico tipo) {
        this.tipo = tipo;
    }

    public TipoTransportePublico getTipo() {return tipo;}

    public void setTipo(TipoTransportePublico tipo) {this.tipo = tipo;}

    public Linea getLinea() {return linea;}

    public void setLinea(Linea linea) {this.linea = linea;}

    public double calcularDistanciaEntreParadas(Parada paradaInicio, Parada paradaFin){

        double acumDistancia = 0;
        int i = 0;
        boolean pasoPorInicio = false;

        while(this.linea.getParadas().get(i) != paradaFin){
            if(this.linea.getParadas().get(i) == paradaInicio){
                pasoPorInicio = true;
            }
            if(pasoPorInicio){
                acumDistancia += this.linea.getParadas().get(i).getDistanciaProximaParada() - this.linea.getParadas().get(i).getDistanciaParadaAnterior();
            }
            i++;
        }
        return acumDistancia;
    }

    public DistanciaAPI distancia(Direccion direccionInicial, Direccion direccionFinal) {
        return null;
    }

}