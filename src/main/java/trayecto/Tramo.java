package trayecto;
import MediosDeTransporte.*;
import domain.services.entities.DistanciaAPI;

import java.io.IOException;
import java.time.LocalDateTime;

public class Tramo {
        private MediosDeTransporte medioDeTransporte;
        private Direccion ubicacionInicio;
        private Direccion ubicacionFinal;
        private DistanciaAPI distancia;
        private LocalDateTime fechaHoraInicio;
        private LocalDateTime fechaHoraFin;


        public DistanciaAPI getDistancia() throws IOException {
                calcularDistanciaTramo();
                return distancia;
        }

        public void calcularDistanciaTramo() throws IOException {
                distancia = medioDeTransporte.distancia(ubicacionInicio, ubicacionFinal);
        }

        public double getCalculoHC(){
                return this.medioDeTransporte.getHC(this.distancia);
        }

        public void setMedioDeTransporte(MediosDeTransporte medioDeTransporte) {
                this.medioDeTransporte = medioDeTransporte;
        }

        public void setUbicacionInicio(Direccion ubicacionInicio) {
                this.ubicacionInicio = ubicacionInicio;
        }

        public void setUbicacionFinal(Direccion ubicacionFinal) {
                this.ubicacionFinal = ubicacionFinal;
        }
}
