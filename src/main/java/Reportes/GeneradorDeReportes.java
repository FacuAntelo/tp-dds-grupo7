package Reportes;


import AgenteSectorial.SectorTerritorial;
import HuellaDeCarbono.HuellaDeCarbono;
import HuellaDeCarbono.RegistroHC;
import Organizacion.Clasificacion;
import Organizacion.Organizacion;
import com.mysql.cj.jdbc.SuspendableXAConnection;
import com.mysql.cj.xdevapi.SessionImpl;
import db.EntityManagerHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import trayecto.Provincia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static HuellaDeCarbono.CalculadoraHC.calcularHCMiembro;

public class GeneradorDeReportes {

    public static void generarReportePorTipoDeOrganizacion(Clasificacion clasificacion){
        System.out.println("REPORTE POR TIPO DE ORGANIZACION: " + clasificacion.getNombre());
        RegistroHC registro = (RegistroHC) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT r from Organizacion as o inner join o.registrosHC as r where o.clasificacion = :clasificacion and r.tipoRegistro = 'TOTAL' order by r.fecha desc", RegistroHC.class)
                .setParameter("clasificacion", clasificacion).getResultList().get(0);

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

    public static List<Organizacion> generarReporteHCPorSectorTerritorial(SectorTerritorial sector){
        List<Organizacion> organizaciones = (List <Organizacion>) EntityManagerHelper.getEntityManager()
                .createQuery("select o from Organizacion as o join o.sectoresTerritoriales as s ",Organizacion.class).getResultList();
        return organizaciones.stream().filter(o->o.getSectoresTerritoriales().stream().map(s -> s.getId()).collect(Collectors.toList()).contains(sector.getId())).collect(Collectors.toList());
    }
;
//    SELECT
//            (select
//                     h1.valor
//                     from huella_de_carbono as h1 inner join registro as r1 on h1.id = r1.valorHCtotal_id
//                     where r1.id_organizacion =.id
//                     order by r.fecha_de_registro desc
//                     LIMIT 1)
//    FROM registro as r inner join huella_de_carbono as h  on r.valorHCTotal_id = h.id
//    inner join organizacion as o on r.id = o.id
//    inner join ubicacion on o.ubicacion_id = ubicacion.id
//    inner join direccion d on d.id = ubicacion.id_direccion
//    inner join provincia p on p.id = d.provincia_id
//    where p.id_sector_territorial = '1'
//    public static void generarReporteComposicionDiscriminadoPorProvinciaMap(){
//        Map<String, Long> registro = (Map<String, Long>) EntityManagerHelper.getEntityManager()
//                .createQuery("SELECT NEW map( p.provincia, sum(r.valorHCTotal.valor)) " +
//                        "from Organizacion as o " +
//                        "inner join o.ubicacion.direccion.provincia as p " +
//                        "inner join o.registrosHC as r where r.tipoRegistro = 'TOTAL' " +
//                        "group by p order by r.fecha desc",Map<String, Long>.class).getFirstResult();
//
//        System.out.println("-----Composición de HC total a nivel país (discriminando provincias):-----");
//
//        registro.forEach((k, v) -> System.out.println(k.toString() + ": " + v.toString()));
//    }







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
