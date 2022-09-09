package HuellaDeCarbono;

import MediosDeTransporte.MediosDeTransporte;
import domain.ubicacion.Ubicacion;
import trayecto.Direccion;
import trayecto.Tramo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CalculadoraHC {

    public List<Tramo> filtrarTramos(List<Tramo> tramos){
        List<Tramo> tramosFiltrados = new ArrayList<>();
        tramos = filtrarTramosCalculados(tramos);
       while(!tramos.isEmpty()){
            Tramo tramo = tramos.remove(0);
            if(tramo.getMedioDeTransporte().getEsCompartido()){
                tramos = tramos.stream().filter(t -> !this.dosTramosSonIguales(tramo,t)).collect(Collectors.toList());
            }
           tramosFiltrados.add(tramo);
        }
        return tramosFiltrados;
    }

    public List<Tramo> filtrarTramosCalculados(List<Tramo> tramos) {
        return tramos.stream().filter(t -> !t.getFueCalculado()).collect(Collectors.toList());
    }

    public Boolean dosTramosSonIguales(Tramo unTramo, Tramo otroTramo) {
        return
          dosLocalTimeSonIguales(unTramo.getHoraInicio(),otroTramo.getHoraInicio()) && dosDireccionesSonIguales(unTramo.getUbicacionInicio(),otroTramo.getUbicacionInicio()) &&
            dosDireccionesSonIguales(unTramo.getUbicacionFinal(),otroTramo.getUbicacionFinal()) &&
              dosMediosDeTransporteSonIguales(unTramo.getMedioDeTransporte(), otroTramo.getMedioDeTransporte());
    }

    public Boolean dosLocalTimeSonIguales (LocalTime unaHora, LocalTime otraHora){
        return unaHora.equals(otraHora);
    }

    public Boolean dosDireccionesSonIguales(Direccion unaDireccion, Direccion otraDireccion){
        // NO COMPARE POR CALLE PORQUE NO SE SI SON KEY SENSISITIVE
        return unaDireccion.getLocalidad().getLocalidad() == otraDireccion.getLocalidad().getLocalidad() && unaDireccion.getAltura() == otraDireccion.getAltura();
    }

    public Boolean dosMediosDeTransporteSonIguales(MediosDeTransporte unMedioDeTranporte,MediosDeTransporte otroMedioDeTransporte){
        return unMedioDeTranporte.getTipoTransporte() == otroMedioDeTransporte.getTipoTransporte();
    }

    public HuellaDeCarbono calcularHCTramos(List <Tramo> tramos){

        return null;
    }
}
