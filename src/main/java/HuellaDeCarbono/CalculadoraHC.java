package HuellaDeCarbono;

import MediosDeTransporte.MediosDeTransporte;
import domain.ubicacion.Ubicacion;
import trayecto.Tramo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CalculadoraHC {

    public List<Tramo> filtrarTramos(List<Tramo> tramos){
        List<Tramo> tramosFiltrados = new ArrayList<>();
        int aux = tramos.size();
        for(int i =0; i< aux; i++){
            Tramo tramo = tramos.remove(0);
            if(tramo.getMedioDeTransporte().getEsCompartido()){
                tramos = tramos.stream().filter(t -> !dosTramosSonIguales(tramo,t)).collect(Collectors.toList());
            }
            tramosFiltrados.add(tramo);
            aux = tramos.size();
        }
        return tramosFiltrados;
    }
    public Boolean dosTramosSonIguales(Tramo unTramo, Tramo otroTramo) {
        // TODO
        return true;
    }
    public Boolean dosUbicacionesSonIguales(Ubicacion unaUbicacion, Ubicacion otraUbicacion){
        // NO COMPARE POR CALLE PORQUE NO SE SI SON KEY SENSISITIVE
        return unaUbicacion.getLocalidad() == otraUbicacion.getLocalidad() && unaUbicacion.getAltura() == otraUbicacion.getAltura();
    }

    public Boolean dosMediosDeTransporteSonIguales(MediosDeTransporte unMedioDeTranporte,MediosDeTransporte otroMedioDeTransporte){
        return unMedioDeTranporte.getTipoTransporte() == otroMedioDeTransporte.getTipoTransporte();
    }

    public HuellaDeCarbono calcularHCTramos(){

        return null;
    }
}
