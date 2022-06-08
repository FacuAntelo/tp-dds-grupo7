package trayecto;
import MediosDeTransporte.*;
import domain.services.entities.DistanciaAPI;

import java.io.IOException;

public class Tramo {
        private MediosDeTransporte medioDeTransporte;
        private Direccion ubicacionInicio;
        private Direccion ubicacionFinal;
        private DistanciaAPI distancia;

        public DistanciaAPI getDistancia() throws IOException {
                calcularDistanciaTramo();
                return distancia;
        }

        public void calcularDistanciaTramo() throws IOException {
                this.distancia = medioDeTransporte.distancia(ubicacionInicio, ubicacionFinal);
        }
}
