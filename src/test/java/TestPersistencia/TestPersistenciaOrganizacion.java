package TestPersistencia;

import HuellaDeCarbono.CalculadoraHC;
import Organizacion.Clasificacion;
import Organizacion.Organizacion;
import Organizacion.TipoOrganizacion;
import Organizacion.Ubicacion;
import db.EntityManagerHelper;
import org.junit.Before;
import org.junit.Test;
import trayecto.Direccion;
import trayecto.Localidad;
import trayecto.Provincia;

import java.io.IOException;
import java.util.List;

import static Organizacion.TipoOrganizacion.EMPRESA;

public class TestPersistenciaOrganizacion {
    Localidad ezeiza;
    @Before
    public void inicializacion() throws IOException {
        Localidad ezeiza = new Localidad(180);
    }

    @Test
    public void persistirOrganizacion(){
        Ubicacion ubicacionCocaCola= new Ubicacion();
        // DIRECCION //
//        Localidad ezeiza = new Localidad(180);
        Provincia buenosAires = new Provincia("Buenos Aires");
        Direccion direccionCocaCola = new Direccion("Medrano",1500,ezeiza,buenosAires);
        // FIN DIRECCION
        ubicacionCocaCola.setCodigoPostal(1804);
        ubicacionCocaCola.setDireccion(direccionCocaCola);
        // FIN UBICACION //
        // TIPO DE LA ORGANIZACION //
        TipoOrganizacion tipoEmpresa = EMPRESA;
        // FIN TIPO DE LA ORGANIZACION //
        // CLASIFICACION //
        Clasificacion clasificacionProductor = new Clasificacion();
        clasificacionProductor.setNombre("Productor");
        // FIN CLASIFICACION //
        Organizacion cocaCola = new Organizacion("Coca Cola Company",tipoEmpresa,clasificacionProductor, ubicacionCocaCola);

        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(direccionCocaCola);
        EntityManagerHelper.getEntityManager().persist(ubicacionCocaCola);
//        EntityManagerHelper.getEntityManager().persist(tipoEmpresa);
//        EntityManagerHelper.getEntityManager().persist(clasificacionProductor);
        EntityManagerHelper.getEntityManager().persist(cocaCola);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
        EntityManagerHelper.getEntityManager().close();
    }

    @Test
    public void recuperarOrganizacionPorLocalidad(){
        int localidad =180;
        List<Organizacion> resultado = (List<Organizacion>) EntityManagerHelper.getEntityManager()
                .createQuery("SELECT o from Organizacion as o left join o.ubicacion.direccion.localidad as l where l.localidad = :localidad", Organizacion.class)
                .setParameter("localidad", localidad)
                .getResultList();

//        resultado.stream().forEach(organizacion -> {
//            ezeiza.getRegistrosHC().add(organizacion.getUltimoRegistroHCTotal());
//        });
//        CalculadoraHC.calcularHC(resultado.get(0));
//        ezeiza.setRegistrosHC(resultado.get(0).getRegistroHCTotales());

        System.out.print(resultado.size());
        System.out.print(resultado.get(0).getRazonSocial());
        System.out.print(resultado.get(0).getRegistrosHC().size());
    }
}
