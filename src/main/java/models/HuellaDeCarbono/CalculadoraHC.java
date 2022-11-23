package models.HuellaDeCarbono;

import models.MediosDeTransporte.MediosDeTransporte;
import models.Miembro.Miembro;
import models.Organizacion.DatosDeActividad;
import models.Organizacion.Organizacion;
import models.db.EntityManagerHelper;
import models.trayecto.Direccion;
import models.trayecto.Localidad;
import models.trayecto.Tramo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalculadoraHC {

    public static List<Tramo> filtrarTramos(List<Tramo> tramos) {
        List<Tramo> tramosFiltrados = new ArrayList<>();
        tramos = filtrarTramosCalculados(tramos);
        while (!tramos.isEmpty()) {
            Tramo tramo = tramos.remove(0);
            if (tramo.getMedioDeTransporte().getEsCompartido()) {
                tramos = tramos.stream().filter(t -> !dosTramosSonIguales(tramo, t)).collect(Collectors.toList());
            }
            tramosFiltrados.add(tramo);
        }
        return tramosFiltrados;
    }

    public static List<Tramo> filtrarTramosCalculados(List<Tramo> tramos) {
        return tramos.stream().filter(t -> !t.getFueCalculado()).collect(Collectors.toList());
    }

    public static List<DatosDeActividad> filtrarDatosCalculados(List<DatosDeActividad> datos) {
        return datos.stream().filter(d -> !d.getSeCalculo()).collect(Collectors.toList());
    }

    public static Boolean dosTramosSonIguales(Tramo unTramo, Tramo otroTramo) {

        if (dosLocalTimeSonIguales(unTramo.getHoraInicio(), otroTramo.getHoraInicio()) && dosDireccionesSonIguales(unTramo.getUbicacionInicio(), otroTramo.getUbicacionInicio()) &&
                dosDireccionesSonIguales(unTramo.getUbicacionFinal(), otroTramo.getUbicacionFinal()) &&
                dosMediosDeTransporteSonIguales(unTramo.getMedioDeTransporte(), otroTramo.getMedioDeTransporte())){
            otroTramo.seCalculo();
            return true;
        } else {
            return false;
        }
    }
    public static RegistroHC calcularHCLocalidad(Localidad localidad) {
        List<Organizacion> organizacionesLocalidad = (List<Organizacion>) EntityManagerHelper.getEntityManager()
                .createQuery("select o from Organizacion as o" +
                        "where o.localidad= :localidad_id", Organizacion.class)
                .setParameter("localidad_id",localidad.getId())
                .getResultList();
        List<RegistroHC> registros = organizacionesLocalidad.stream().map(o -> o.calcularHC()).collect(Collectors.toList());

        return RegistroHC.unificarRegistros(registros);

    }
    public static Boolean dosLocalTimeSonIguales(LocalTime unaHora, LocalTime otraHora) {
        return unaHora.equals(otraHora);
    }

    public static Boolean dosDireccionesSonIguales(Direccion unaDireccion, Direccion otraDireccion) {
        // NO COMPARE POR CALLE PORQUE NO SE SI SON KEY SENSISITIVE
        return unaDireccion.getLocalidad() == otraDireccion.getLocalidad() && unaDireccion.getAltura() == otraDireccion.getAltura();
    }

    public static Boolean dosMediosDeTransporteSonIguales(MediosDeTransporte unMedioDeTranporte, MediosDeTransporte otroMedioDeTransporte) {
        return unMedioDeTranporte.getTipoTransporte() == otroMedioDeTransporte.getTipoTransporte();
    }

    public static int calcularHCTramos(List<Tramo> tramos) {
        tramos = filtrarTramos(tramos);
        int valor = tramos.stream().mapToInt(tramo -> {
            tramo.seCalculo();
            int valor_ = (int) ((int) tramo.getDistancia().getValor() * tramo.getMedioDeTransporte().getConsumoXKM() * tramo.getMedioDeTransporte().getValorFactorDeEmision());
            System.out.println(valor_);
            return valor_;
        }).sum();
        return valor;
    }

    public static double calcularHCDatosDeActividad(List<DatosDeActividad> datosDeActividad) {
        datosDeActividad = filtrarDatosCalculados(datosDeActividad);
        return datosDeActividad.stream().mapToDouble(dato -> {
            dato.seCalculo();
            return dato.getValor() * dato.getFactorDeEmision().getValorFactorEmision();
        }).sum();
    }

    public static RegistroHC calcularHC(Organizacion organizacion) {
        int HCtrayectos = calcularHCTramos(organizacion.obtenerTramosDeLosMiembros());
        HuellaDeCarbono huellaHC = new HuellaDeCarbono(HCtrayectos);
        int HCdatos = (int) calcularHCDatosDeActividad(organizacion.getDatosDeActividad());
        HuellaDeCarbono huellaDA = new HuellaDeCarbono(HCdatos);
        HuellaDeCarbono huellaTotal = huellaDA.suma(huellaHC);
        RegistroHC registro = new RegistroHC(huellaDA, huellaHC, huellaTotal, TipoRegistro.PARCIAL);
        return registro;
    }

    public static HuellaDeCarbono calcularHCMiembro(Miembro miembro) {
        if(!miembro.getTramos().isEmpty()){
            int hc = miembro.getTramos().stream().mapToInt(tramo -> {
                int valor_ = (int) ((int) tramo.getDistancia().getValor() * tramo.getMedioDeTransporte().getConsumoXKM() * tramo.getMedioDeTransporte().getValorFactorDeEmision());
                return valor_;
            }).sum();

            return new HuellaDeCarbono(hc);
        }
        else{
            return new HuellaDeCarbono(0);
        }
    }
    public static void miembroHCrespectoOrganizacion(Miembro miembro,Organizacion organizacion){
        System.out.println("IMPACTO DE MIEMBRO EN ORGANIZACION");
        HuellaDeCarbono orgTotalHC = organizacion.getRegistroHCTotales().get(organizacion.getRegistroHCTotales().size()-1).getValorHCTotal();
        HuellaDeCarbono miembroHC =  calcularHCMiembro(miembro);
        System.out.println("HUELLA DE CARBONO DEL MIEMBRO " + miembro.getNombre() + " " + miembro.getApellido() + ": " + miembroHC.getValor() + miembroHC.getUnidad());
        System.out.println("HUELLA DE CARBONO DE LA ORG "+ organizacion.getRazonSocial() +": " + orgTotalHC.getValor() + orgTotalHC.getUnidad());
        System.out.println("IMPACTO DEL: " + (double) miembroHC.getValor()/orgTotalHC.getValor()+"%");
        System.out.println();
    }

    public static void calculoDeHCdeSectores(Organizacion organizacion){
        System.out.println("IMPACTO DE LOS SECTORES DE LA ORGANIZACION: " + organizacion.getRazonSocial());

        if(!organizacion.getSectores().isEmpty()){
            organizacion.getSectores().stream().forEach(sector -> {
                if(!sector.getMiembros().isEmpty()){
                    System.out.println("IMPACTO DEL SECTOR " + sector.getNombre());
                    int valorHC = sector.getMiembros().stream().mapToInt(m -> calcularHCMiembro(m).getValor()).sum();
                    HuellaDeCarbono hc = new HuellaDeCarbono(valorHC);
                    System.out.println(hc.getValorConUnidad());
                }
            });

        }
    }
}