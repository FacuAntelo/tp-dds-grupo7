package Reportes;

import AgenteSectorial.SectorTerritorial;
import HuellaDeCarbono.*;
import Miembro.Miembro;
import Organizacion.Clasificacion;
import Organizacion.Organizacion;
import db.EntityManagerHelper;
import trayecto.Provincia;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static HuellaDeCarbono.CalculadoraHC.calcularHCMiembro;

public class GeneradorDeReportes {

    public static void generarReportePorTipoDeOrganizacion(Clasificacion clasificacion){
        List<Organizacion> organizaciones = (List<Organizacion>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT o from Organizacion as o  where o.clasificacion = :clasificacion ", Organizacion.class)
                .setParameter("clasificacion", clasificacion).getResultList();
        List <RegistroHC> registros = organizaciones.stream().map(o -> o.devolverUltimoRegistro()).collect(Collectors.toList());
        RegistroHC registroUnificado = RegistroHC.unificarRegistros(registros);

        System.out.println("\n-----HC total por tipo de Organización (según la clasificación de la Organización):-----");
        System.out.println("REPORTE POR TIPO DE ORGANIZACION: " + clasificacion.getNombre());
        System.out.println(clasificacion.getNombre()+ ": "+ registroUnificado.getValorHCTotal().getValorConUnidad());
    }

    public static void generarReporteDeOrganizacion(Organizacion organizacion){
        System.out.println("\n-----Composición de HC total de una determinada Organización:-----");
        CalculadoraHC.calculoDeHCdeSectores(organizacion);
    }

    public static void generarReporteEvolutivoDeOrganizacion(Organizacion organizacion){
        List<RegistroHC> registro = (List<RegistroHC>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT r from Organizacion as o inner join o.registrosHC as r where o.id = :organizacionId and r.tipoRegistro = 'TOTAL' order by r.fecha desc", RegistroHC.class)
                .setParameter("organizacionId", organizacion.getId()).getResultList();

        System.out.println("\n-----Evolución de HC total de una determinada Organización:-----");
        System.out.println(organizacion.getRazonSocial());
        registro.forEach(r -> System.out.println(r.getFecha() + ": " + r.getValorHCTotal().getValor()));
    }

    public static void generarReporteComposicionDiscriminadoPorProvincia(){
//        List<ReportePorProvincia> registro = (List<ReportePorProvincia>) EntityManagerHelper.getEntityManager()
//                .createQuery("SELECT NEW Reportes.ReportePorProvincia( p, sum(r.valorHCTotal.valor)) " +
//                                "from Organizacion as o " +
//                                "inner join o.ubicacion.direccion.provincia as p " +
//                                "inner join o.registrosHC as r where r.tipoRegistro = 'TOTAL' " +
//                                "group by p order by r.fecha desc", ReportePorProvincia.class)
//                .getResultList();
        System.out.println("\n-----Composición de HC total a nivel país (discriminando provincias):-----");
        List<Provincia> provincias = EntityManagerHelper.getEntityManager()
                .createQuery("SELECT p from Provincia as p  ", Provincia.class)
                .getResultList();
        System.out.println("Cantidad provincia: "+provincias.size());

        provincias.forEach( provincia -> {
            RegistroHC resultado =  provincia.calcularHC();
            System.out.println(provincia.getProvincia() + ": " + resultado.getValorHCTotal().getValorConUnidad());
        });
//        registro.forEach(r -> System.out.println(r.getProvincia().getProvincia() + ": "+ r.getValor()));
    }

    public static void generarReporteHCTotalPorSectorTerritorial(){
        System.out.println("\n-----HC total por sector territorial:-----");
        List<SectorTerritorial> sectores = EntityManagerHelper.getEntityManager()
                .createQuery("select s from SectorTerritorial as s  ",SectorTerritorial.class).getResultList();

        sectores.forEach(sector ->{
            List<Organizacion> organizaciones = (List <Organizacion>) EntityManagerHelper.getEntityManager()
                    .createQuery("select o from Organizacion as o ",Organizacion.class).getResultList();
            organizaciones =  organizaciones.stream().filter(o->o.getSectoresTerritoriales().stream().map(s -> s.getId()).collect(Collectors.toList()).contains(sector.getId())).collect(Collectors.toList());
            List <RegistroHC> registros = organizaciones.stream().map(o -> o.devolverUltimoRegistro()).collect(Collectors.toList());

            RegistroHC registroUnificado = RegistroHC.unificarRegistros(registros);
            registroUnificado.setTipoRegistro(TipoRegistro.TOTAL);

//            SE REEMPLAZAN LAS LINEAS COMENTADAS POR LOS DOS METODOS DE ARRIBA
//            HuellaDeCarbono huellaTotal = new HuellaDeCarbono();
//            huellaTotal.setValor(registros.stream().mapToInt(r-> r.getValorHCTotal().getValor()).sum());
//            HuellaDeCarbono huellaDatos = new HuellaDeCarbono();
//            huellaDatos.setValor(registros.stream().mapToInt(r-> r.getValorHCDatoActividad().getValor()).sum());
//            HuellaDeCarbono huellaTrayectos = new HuellaDeCarbono();
//            huellaTrayectos.setValor(registros.stream().mapToInt(r-> r.getValorHCTrayecto().getValor()).sum());
//
//            RegistroHC registroHC = new RegistroHC(huellaDatos,huellaTrayectos,huellaTotal, TipoRegistro.TOTAL);

            sector.agregarRegistro(registroUnificado);
            System.out.println("Del sector: " + sector.getId() +" se obtuvo el valor TOTAL: " + registroUnificado.getValorHCTotal().getValorConUnidad());
//            System.out.println("Del sector: " + sector.getId() +" se obtuvo el valor DATOS DE ACTIVIDAD: " + huellaDatos.getValorConUnidad());
//            System.out.println("Del sector: " + sector.getId() +" se obtuvo el valor TRAYECTOS: " + huellaTrayectos.getValorConUnidad());
        });
    }

    public static void generarReporteHCComposicionTotalDeUnSectorTerritorial(SectorTerritorial sector){
        List<Organizacion> organizaciones = (List <Organizacion>) EntityManagerHelper.getEntityManager()
                .createQuery("select o from Organizacion as o  ",Organizacion.class).getResultList();
        organizaciones =  organizaciones.stream().filter(o->o.getSectoresTerritoriales().stream().map(s -> s.getId()).collect(Collectors.toList()).contains(sector.getId())).collect(Collectors.toList());

        System.out.println("\n-----Composición de HC total de un determinado sector territorial:-----");
        System.out.println("REPORTE POR  SECTOR TERRITORIAL: " + sector.getId());
        organizaciones.forEach(o -> System.out.println("Organizacion: " + o.getId() + " obtuvo valor total: "+ o.devolverUltimoRegistro().getValorHCTotal().getValorConUnidad()));
    }

    public static void generarReporteHCEvolucionDeSectorTerritorial(SectorTerritorial sector){
        List<RegistroHC> registros = EntityManagerHelper.getEntityManager()
                .createQuery("select r " +
                        "from Organizacion as o " +
                        "inner join o.sectoresTerritoriales as s " +
                        "inner join o.registrosHC as r " +
                        "where s.id= :id_sectorTerritorial and r.tipoRegistro= 'TOTAL'" +
                        "order by r.fecha desc ",RegistroHC.class)
                .setParameter("id_sectorTerritorial", sector.getId())
                .getResultList();
        System.out.println("\n-----Evolución de HC total de un determinado sector territorial:-----");
        System.out.println("Sector territorial: " + sector.getId());
        registros.forEach(r ->System.out.println( r.getFecha() + ": " + r.getValorHCTotal().getValorConUnidad()) );

    }


    public static void reporteDeHCdeSectores(Organizacion organizacion){
        System.out.println("IMPACTO DE LOS SECTORES DE LA ORGANIZACION: " + organizacion.getRazonSocial());
        organizacion.getSectores().stream().forEach(sector -> {
            System.out.println("IMPACTO DEL SECTOR " + sector.getNombre());
            int valorHC = sector.getMiembros().stream().mapToInt(m -> calcularHCMiembro(m).getValor()).sum();
            HuellaDeCarbono hc = new HuellaDeCarbono(valorHC);
            System.out.println(hc.getValorConUnidad());
        });
    }

}
