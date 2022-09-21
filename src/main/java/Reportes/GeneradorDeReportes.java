package Reportes;

import HuellaDeCarbono.RegistroHC;
import Organizacion.Clasificacion;
import Organizacion.Organizacion;
import db.EntityManagerHelper;
import trayecto.Provincia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneradorDeReportes {

    public static void generarReportePorTipoDeOrganizacion(Clasificacion clasificacion){
        RegistroHC registro = (RegistroHC) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT r from Organizacion as o inner join o.registrosHC as r where o.clasificacion = :clasificacion and r.tipoRegistro = 'TOTAL' order by r.fecha desc", RegistroHC.class)
                .setParameter("clasificacion", clasificacion).getResultList().get(0);

        System.out.println("-----HC total por tipo de Organización (según la clasificación de la Organización):-----");
        System.out.println(clasificacion.getNombre()+ ": "+ registro.getValorHCTotal().getValor());
    }

    public static void generarReporteDeOrganizacion(Organizacion organizacion){
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


}
