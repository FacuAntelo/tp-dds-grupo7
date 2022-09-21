package Reportes;

import HuellaDeCarbono.RegistroHC;
import Organizacion.Clasificacion;
import Organizacion.Organizacion;
import db.EntityManagerHelper;

public class GeneradorDeReportes {

    public static void generarReportePorTipoDeOrganizacion(Clasificacion clasificacion){
        RegistroHC registro = (RegistroHC) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT r from Organizacion as o inner join o.registrosHC as r where o.clasificacion = :clasificacion order by r.fecha desc", RegistroHC.class)
                .setParameter("clasificacion", clasificacion).getResultList().get(0);

        System.out.println(registro.getValorHCTotal().getValor());
    }

}
