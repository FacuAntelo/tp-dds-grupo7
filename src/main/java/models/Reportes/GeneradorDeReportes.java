package models.Reportes;

import models.AgenteSectorial.SectorTerritorial;
import models.Organizacion.Clasificacion;
import models.Organizacion.Organizacion;
import models.db.EntityManagerHelper;
import models.HuellaDeCarbono.CalculadoraHC;
import models.HuellaDeCarbono.HuellaDeCarbono;
import models.HuellaDeCarbono.RegistroHC;
import models.HuellaDeCarbono.TipoRegistro;
import models.trayecto.Provincia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GeneradorDeReportes {
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

            //Estas dos lineas hay que eliminar??
            registroUnificado.setTipoRegistro(TipoRegistro.TOTAL);
            sector.agregarRegistro(registroUnificado);

            System.out.println("Del sector: " + sector.getId() +" se obtuvo el valor TOTAL: " + registroUnificado.getValorHCTotal().getValorConUnidad());
        });
    }

    public static void generarReporteHCTotalPorTipoDeOrganizacion(Clasificacion clasificacion){
        List<Organizacion> organizaciones = (List<Organizacion>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT o from Organizacion as o  where o.clasificacion = :clasificacion ", Organizacion.class)
                .setParameter("clasificacion", clasificacion).getResultList();
        List <RegistroHC> registros = organizaciones.stream().map(o -> o.devolverUltimoRegistro()).collect(Collectors.toList());
        RegistroHC registroUnificado = RegistroHC.unificarRegistros(registros);

        System.out.println("\n-----HC total por tipo de Organización (según la clasificación de la Organización):-----");
        System.out.println("REPORTE POR TIPO DE ORGANIZACION: " + clasificacion.getNombre());
        System.out.println(clasificacion.getNombre()+ ": "+ registroUnificado.getValorHCTotal().getValorConUnidad());
    }

    public static void generarReporteComposicionHCTotalDeUnSectorTerritorial(SectorTerritorial sector){
        List<Organizacion> organizaciones = (List <Organizacion>) EntityManagerHelper.getEntityManager()
                .createQuery("select o from Organizacion as o  ",Organizacion.class).getResultList();
        organizaciones =  organizaciones.stream().filter(o->o.getSectoresTerritoriales().stream().map(s -> s.getId()).collect(Collectors.toList()).contains(sector.getId())).collect(Collectors.toList());

        System.out.println("\n-----Composición de HC total de un determinado sector territorial:-----");
        System.out.println("REPORTE POR  SECTOR TERRITORIAL: " + sector.getId());
        organizaciones.forEach(o -> System.out.println("Organizacion: " + o.getId() + " obtuvo valor total: "+ o.devolverUltimoRegistro().getValorHCTotal().getValorConUnidad()));
    }

    public static void generarReporteComposicionHCTotalDiscriminadoPorProvincia(){
        System.out.println("\n-----Composición de HC total a nivel país (discriminando provincias):-----");
        List<Provincia> provincias = EntityManagerHelper.getEntityManager()
                .createQuery("SELECT p from Provincia as p  ", Provincia.class)
                .getResultList();
        System.out.println("Cantidad provincia: "+provincias.size());

        provincias.forEach( provincia -> {
            RegistroHC resultado =  provincia.calcularHC();
            System.out.println(provincia.getProvincia() + ": " + resultado.getValorHCTotal().getValorConUnidad());
        });
    }

    public static void ggenerarReporteComposicionHCTotalDeOrganizacion(Organizacion organizacion){
        System.out.println("\n-----Composición de HC total de una determinada Organización:-----");
        CalculadoraHC.calculoDeHCdeSectores(organizacion);
    }

    public static void generarReporteEvolucionHCTotalDeSectorTerritorial(SectorTerritorial sector){
        List<Organizacion> organizaciones = (List <Organizacion>) EntityManagerHelper.getEntityManager()
                .createQuery("select o from Organizacion as o ",Organizacion.class).getResultList();
        organizaciones =  organizaciones.stream().filter(o->o.getSectoresTerritoriales().stream().map(s -> s.getId()).collect(Collectors.toList()).contains(sector.getId())).collect(Collectors.toList());

        List<RegistroHC> registros = new ArrayList<>();
        organizaciones.forEach(o -> registros.addAll(o.getRegistrosHC().stream().filter(r -> r.getTipoRegistro() == TipoRegistro.TOTAL).collect(Collectors.toList())));
        registros.sort(Comparator.comparing(a -> a.getFecha()));

        System.out.println("\n-----Evolución de HC total de un determinado sector territorial:-----");
        System.out.println("Sector territorial: " + sector.getId());
        registros.forEach(r -> System.out.println( r.getFecha() + ": " + r.getValorHCTotal().getValorConUnidad()));
    }

    public static void generarReporteEvolucionHCTotalDeOrganizacion(Organizacion organizacion){
        List<RegistroHC> registro = (List<RegistroHC>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT r from Organizacion as o inner join o.registrosHC as r where o.id = :organizacionId and r.tipoRegistro = 'TOTAL' order by r.fecha desc", RegistroHC.class)
                .setParameter("organizacionId", organizacion.getId()).getResultList();

        System.out.println("\n-----Evolución de HC total de una determinada Organización:-----");
        System.out.println(organizacion.getRazonSocial());
        registro.forEach(r -> System.out.println(r.getFecha() + ": " + r.getValorHCTotal().getValor()));
    }


    public static List<ReporteSectoresOrganizacionDTO> reporteDeHCdeSectores(Organizacion organizacion){
        List<ReporteSectoresOrganizacionDTO> reportes=new ArrayList<ReporteSectoresOrganizacionDTO>();

        System.out.println("IMPACTO DE LOS SECTORES DE LA ORGANIZACION: " + organizacion.getRazonSocial());
        organizacion.getSectores().stream().forEach(sector -> {
            System.out.println("IMPACTO DEL SECTOR " + sector.getNombre());
            int valorHC = sector.getMiembros().stream().mapToInt(m -> CalculadoraHC.calcularHCMiembro(m).getValor()).sum();
            HuellaDeCarbono hc = new HuellaDeCarbono(valorHC);
            System.out.println(hc.getValorConUnidad());
            reportes.add(new ReporteSectoresOrganizacionDTO(sector.getNombre(), hc.getValorConUnidad()));
        });
        return reportes;
    }

}
