package Reportes;

import HuellaDeCarbono.RegistroHC;
import Organizacion.Clasificacion;
import Organizacion.Organizacion;
import db.EntityManagerHelper;

import java.util.List;

public class GeneradorDeReportes {

    public static void generarReportePorTipoDeOrganizacion(Clasificacion clasificacion){
        RegistroHC registro = (RegistroHC) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT r from Organizacion as o inner join o.registrosHC as r where o.clasificacion = :clasificacion and r.tipoRegistro = 'TOTAL' order by r.fecha desc", RegistroHC.class)
                .setParameter("clasificacion", clasificacion).getResultList().get(0);

        System.out.println(registro.getValorHCTotal().getValor());
    }

    public static void generarReporteDeOrganizacion(Organizacion organizacion){
        RegistroHC registro = (RegistroHC) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT r from Organizacion as o inner join o.registrosHC as r where o.id = :organizacionId and r.tipoRegistro = 'TOTAL' order by r.fecha desc", RegistroHC.class)
                .setParameter("organizacionId", organizacion.getId()).getResultList().get(0);

        System.out.println(registro.getValorHCTotal().getValor());
    }
    public static void generarReporteEvolutivoDeOrganizacion(Organizacion organizacion){
        List<RegistroHC> registro = (List<RegistroHC>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT r from Organizacion as o inner join o.registrosHC as r where o.id = :organizacionId and r.tipoRegistro = 'TOTAL' order by r.fecha desc", RegistroHC.class)
                .setParameter("organizacionId", organizacion.getId()).getResultList();

        registro.forEach(r -> System.out.println(r.getValorHCTotal().getValor()));
    }


}
