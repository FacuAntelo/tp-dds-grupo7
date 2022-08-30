package trayecto;
import MediosDeTransporte.*;
import domain.services.ServicioGeoDDS;
import domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import domain.services.entities.DistanciaAPI;

import java.io.IOException;
import java.time.LocalDateTime;

public class Tramo {
        private MediosDeTransporte medioDeTransporte;
        private Direccion ubicacionInicio;
        private Direccion ubicacionFinal;
        private DistanciaAPI distancia;
        private LocalDateTime fechaHoraInicio;
        private Boolean fueCalculado = false;

        public Tramo(Direccion ubicacionInicio, Direccion ubicacionFinal) throws IOException {
                this.ubicacionFinal=ubicacionFinal;
                this.ubicacionInicio=ubicacionInicio;
                this.calcularDistanciaTramo();
        }

        public void calcularDistanciaTramo() throws IOException {
                ServicioGeoDDS servicioGeoDDS = ServicioGeoDDS.getInstance();
                this.distancia = servicioGeoDDS.distanciaAPI(ubicacionInicio.getLocalidad().getNumeroLocalidad(), ubicacionInicio.getCalle(), ubicacionInicio.getAltura(),
                        ubicacionFinal.getLocalidad().getNumeroLocalidad(), ubicacionFinal.getCalle(), ubicacionFinal.getAltura());
        }

        public double getCalculoHC() throws IOException {
                return this.medioDeTransporte.getHC(this.distancia);

        }

        public void seCalculo(){
                this.fueCalculado= true;
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
