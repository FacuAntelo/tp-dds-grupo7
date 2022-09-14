package trayecto;
import EntidadPersistente.EntidadPersistente;
import MediosDeTransporte.*;
import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;
import converters.ConverterAttributeLocalTime;
import domain.services.ServicioGeoDDS;
import domain.services.entities.DistanciaAPI;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tramo")
public class Tramo extends EntidadPersistente {
        @OneToOne
        @JoinColumn(name = "id_medio_de_transporte")
        private MediosDeTransporte medioDeTransporte;

        @ManyToOne
        @JoinColumn(name= "direccion_inicio_id",referencedColumnName = "id")
        private Direccion ubicacionInicio;

        @ManyToOne
        @JoinColumn(name= "direccion_final_id",referencedColumnName = "id")
        private Direccion ubicacionFinal;

        @Embedded
        private DistanciaAPI distancia;

        @Column(name="hora_inicio",columnDefinition = "TIME")
        private LocalTime horaInicio;

        @Column(name = "fue_calculado")
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
