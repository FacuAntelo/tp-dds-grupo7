package Reportes;


import AgenteSectorial.SectorTerritorial;
import HuellaDeCarbono.*;
import Miembro.Miembro;
import Organizacion.Clasificacion;
import Organizacion.Organizacion;
import com.mysql.cj.jdbc.SuspendableXAConnection;
import com.mysql.cj.xdevapi.SessionImpl;
import db.EntityManagerHelper;
import org.apache.commons.math3.geometry.hull.ConvexHull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import trayecto.Provincia;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static HuellaDeCarbono.CalculadoraHC.calcularHCMiembro;

public class GeneradorDeReportes {

    public static void generarReportePorTipoDeOrganizacion(Clasificacion clasificacion){
        System.out.println("REPORTE POR TIPO DE ORGANIZACION: " + clasificacion.getNombre());
        List<Organizacion> organizaciones = (List<Organizacion>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT o from Organizacion as o  where o.clasificacion = :clasificacion ", Organizacion.class)
                .setParameter("clasificacion", clasificacion).getResultList();
        List <RegistroHC> registros = organizaciones.stream().map(o -> o.devolverUltimoRegistro()).collect(Collectors.toList());
        int valorDA = registros.stream().mapToInt(r-> r.getValorHCDatoActividad().getValor()).sum();
        int valorTrayecto = registros.stream().mapToInt(r-> r.getValorHCTrayecto().getValor()).sum();
        int valorTotal = registros.stream().mapToInt(r-> r.getValorHCTotal().getValor()).sum();
        RegistroHC registro = RegistroHC.getRegistro(valorDA,valorTrayecto,valorTotal);
        System.out.println("-----HC total por tipo de Organización (según la clasificación de la Organización):-----");
        System.out.println(clasificacion.getNombre()+ ": "+ registro.getValorHCTotal().getValor());
    }

    public static void generarReporteDeOrganizacion(Organizacion organizacion){
        System.out.println("REPORTE POR  ORGANIZACION: " + organizacion.getRazonSocial());
        RegistroHC registro = (RegistroHC) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT r from Organizacion as o inner join o.registrosHC as r where o.id = :organizacionId and r.tipoRegistro = 'TOTAL' order by r.fecha desc", RegistroHC.class)
                .setParameter("organizacionId", organizacion.getId()).getResultList().get(0);

        System.out.println("-----Composición de HC total de una determinada Organización:-----");
        System.out.println(organizacion.getRazonSocial() + ": " + registro.getValorHCTotal().getValor());
    }

    public static void generarReporteEvolutivoDeOrganizacion(Organizacion organizacion){
        List<RegistroHC> registro = (List<RegistroHC>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT r from Organizacion as o inner join o.registrosHC as r where o.id = :organizacionId and r.tipoRegistro = 'TOTAL' order by r.fecha desc", RegistroHC.class)
                .setParameter("organizacionId", organizacion.getId()).getResultList();

        System.out.println("-----Evolución de HC total de una determinada Organización:-----");
        System.out.println(organizacion.getRazonSocial());
        registro.forEach(r -> System.out.println(r.getFecha() + ": " + r.getValorHCTotal().getValor()));
    }

    public static void generarReporteComposicionDiscriminadoPorProvincia(){
        List<ReportePorProvincia> registro = (List<ReportePorProvincia>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT NEW Reportes.ReportePorProvincia( p, sum(r.valorHCTotal.valor)) " +
                                "from Organizacion as o " +
                                "inner join o.ubicacion.direccion.provincia as p " +
                                "inner join o.registrosHC as r where r.tipoRegistro = 'TOTAL' " +
                                "group by p order by r.fecha desc", ReportePorProvincia.class)
                .getResultList();

        System.out.println("-----Composición de HC total a nivel país (discriminando provincias):-----");
        registro.forEach(r -> System.out.println(r.getProvincia().getProvincia() + ": "+ r.getValor()));
    }

//    public static void generarReporteHCPorSectorTerritorial(SectorTerritorial sector){
//        List<Organizacion> organizaciones = (List <Organizacion>) EntityManagerHelper.getEntityManager()
//                .createQuery("select o from Organizacion as o join o.sectoresTerritoriales as s ",Organizacion.class).getResultList();
//       organizaciones =  organizaciones.stream().filter(o->o.getSectoresTerritoriales().stream().map(s -> s.getId()).collect(Collectors.toList()).contains(sector.getId())).collect(Collectors.toList());
//       System.out.println(organizaciones.size());
//
//       List <RegistroHC> registros = organizaciones.stream().map(o -> o.devolverUltimoRegistro()).collect(Collectors.toList());
//       HuellaDeCarbono huellaTotal = new HuellaDeCarbono();
//       huellaTotal.setValor(registros.stream().mapToInt(r-> r.getValorHCTotal().getValor()).sum());
//       HuellaDeCarbono huellaDatos = new HuellaDeCarbono();
//       huellaDatos.setValor(registros.stream().mapToInt(r-> r.getValorHCDatoActividad().getValor()).sum());
//       HuellaDeCarbono huellaTrayectos = new HuellaDeCarbono();
//       huellaTrayectos.setValor(registros.stream().mapToInt(r-> r.getValorHCTrayecto().getValor()).sum());
//
//       RegistroHC registroHC = new RegistroHC(huellaDatos,huellaTrayectos,huellaTotal, TipoRegistro.TOTAL);
//
//       sector.agregarRegistro(registroHC);
//
//        System.out.println("-----Composición de HC total de un determinado sector territorial:-----");
//       System.out.println("Del sector: " + sector.getId() +" se obtuvo el valor TOTAL: " + huellaTotal.getValorConUnidad());
//       System.out.println("Del sector: " + sector.getId() +" se obtuvo el valor DATOS DE ACTIVIDAD: " + huellaDatos.getValorConUnidad());
//       System.out.println("Del sector: " + sector.getId() +" se obtuvo el valor TRAYECTOS: " + huellaTrayectos.getValorConUnidad());
//    }
    public static void generarReporteHCPorSectorTerritorial(SectorTerritorial sector){
        List<ReporteOrganizacionSectorTerritorial> registros = EntityManagerHelper.getEntityManager()
                .createQuery("select NEW Reportes.ReporteOrganizacionSectorTerritorial(o, r.id, max(r.fecha), r.valorHCTotal)" +
                                "from Organizacion as o " +
                                "inner join o.sectoresTerritoriales as s " +
                                "inner join o.registrosHC as r " +
                                "where s.id= :id_sectorTerritorial and r.tipoRegistro= 'TOTAL'" +
                                "group by o ",ReporteOrganizacionSectorTerritorial.class)
                .setParameter("id_sectorTerritorial", sector.getId())
                .getResultList();

        System.out.println("-----Composición de HC total de un determinado sector territorial:-----");
        registros.forEach(r ->System.out.println("Organizacion con id: " + r.getOrganizacion().getId() + ": " + r.getValorHCTotal().getValorConUnidad()) );

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
    public static void generarReporteHCPorMiembroDeOrganizacion(Organizacion organizacion){
        List<ReporteNombreValor> registro = (List<ReporteNombreValor>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT NEW Reportes.ReporteNombreValor( m.nombre, sum(r.valorHCTotal.valor)) "+
                        "from Organizacion as o " +
                        "inner join o.sectores as s " +
                        "inner join s.miembros as m " +
                        "inner join o.registrosHC as r where o.id= :organizacionId and r.tipoRegistro = 'TOTAL' " +
                        "group by m.id ", ReporteNombreValor.class)
                .setParameter("organizacionId", organizacion.getId())
                .getResultList();

        System.out.println("-----Registro de HC por Miembro de la organizacion:-----");
        registro.forEach(r-> System.out.println(r.getNombre()+": "+r.getValor()));
    }

    public static void generarReporteHCPorSectorDeOrganizacion(Organizacion organizacion){
        List<ReporteNombreValor> registro = (List<ReporteNombreValor>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT NEW Reportes.ReporteNombreValor( s.nombre, sum(r.valorHCTotal.valor)) "+
                        "from Organizacion as o " +
                        "inner join o.sectores as s " +
                        "inner join s.miembros as m " +
                        "inner join o.registrosHC as r where o.id= :organizacionId and r.tipoRegistro = 'TOTAL' " +
                        "group by s.id ", ReporteNombreValor.class)
                .setParameter("organizacionId", organizacion.getId())
                .getResultList();

        System.out.println("-----Registro de HC por Sector de la organizacion:-----");
        registro.forEach(r-> System.out.println(r.getNombre()+": "+r.getValor()));
    }

}
