package trayecto;
import MediosDeTransporte.*;
import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;
import domain.services.ServicioGeoDDS;
import domain.services.entities.DistanciaAPI;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Tramo {
        private MediosDeTransporte medioDeTransporte;
        private Direccion ubicacionInicio;
        private Direccion ubicacionFinal;
        private DistanciaAPI distancia;
        private LocalTime horaInicio;
        private Boolean fueCalculado = false;

        public LocalTime getHoraInicio() {
                return horaInicio;
        }

        public void setHoraInicio(LocalTime horaInicio) {
                this.horaInicio = horaInicio;
        }

        public MediosDeTransporte getMedioDeTransporte() {
                return medioDeTransporte;
        }

        public Direccion getUbicacionInicio() {
                return ubicacionInicio;
        }

        public Direccion getUbicacionFinal() {
                return ubicacionFinal;
        }

        public Tramo(Direccion ubicacionInicio, Direccion ubicacionFinal, LocalTime hora) throws IOException {
                this.ubicacionFinal=ubicacionFinal;
                this.ubicacionInicio=ubicacionInicio;
                this.calcularDistanciaTramo();
                this.horaInicio = hora;
        }

        public void calcularDistanciaTramo() throws IOException {
                ServicioGeoDDS servicioGeoDDS = ServicioGeoDDS.getInstance();
                this.distancia = servicioGeoDDS.distanciaAPI(ubicacionInicio.getLocalidad().getNumeroLocalidad(), ubicacionInicio.getCalle(), ubicacionInicio.getAltura(),
                        ubicacionFinal.getLocalidad().getNumeroLocalidad(), ubicacionFinal.getCalle(), ubicacionFinal.getAltura());
        }

        public DistanciaAPI getDistancia() {
                return distancia;
        }

        public double getCalculoHC() throws IOException {
                return this.medioDeTransporte.getHC(this.distancia);

        }

        public void seCalculo(){
                this.fueCalculado= true;
        }

        public Boolean getFueCalculado() {
                return fueCalculado;
        }
        public void setFueCalculado(){
                fueCalculado = true;
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
