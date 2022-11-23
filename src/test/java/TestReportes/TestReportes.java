package TestReportes;

import models.HuellaDeCarbono.RegistroHC;
import models.Organizacion.Organizacion;
import models.Reportes.GeneradorDeReportes;
import models.db.EntityManagerHelper;
import models.trayecto.Localidad;
import models.trayecto.Provincia;
import org.junit.Test;
import repositories.RepositorioLocalidad;
import repositories.RepositorioOrganizacion;
import repositories.RepositorioProvincia;
import repositories.RepositorioSectorTerritorial;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TestReportes {
    @Test
    public void test() throws IOException {
        RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
        RepositorioSectorTerritorial repositorioSectorTerritorial = new RepositorioSectorTerritorial();

        GeneradorDeReportes.generarReporteComposicionHCTotalDiscriminadoPorProvincia();
//        GeneradorDeReportes.generarReporteComposicionHCTotalDeOrganizacion(repositorioOrganizacion.buscar(2));

//        GeneradorDeReportes.generarReporteEvolucionHCTotalDeOrganizacion(repositorioOrganizacion.buscar(1));
    }

    @Test
    public void test2(){
        RepositorioLocalidad repositorioLocalidad=new RepositorioLocalidad();
        RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
        RepositorioProvincia repositorioProvincia = new RepositorioProvincia();

        Provincia provincia = repositorioProvincia.buscarPorId(6);

        Localidad ezeiza = repositorioLocalidad.buscarPorId(Long.parseLong("6270010001"));
//        List<Organizacion> organizacionesLocalidad = (List<Organizacion>) EntityManagerHelper.getEntityManager()
//                .createQuery("select o from Organizacion as o" +
//                        " inner join o.localidad as l" +
//                        " where l.id= :localidad_id", Organizacion.class)
//                .setParameter("localidad_id",Long.parseLong("6270010001"))
//                .getResultList();
        List<Organizacion> organizacionesLocalidad =repositorioOrganizacion.buscarOrganizacionesDeLaLocalidad(Long.parseLong("6270010001"));
        organizacionesLocalidad = organizacionesLocalidad.stream().filter(o-> !(o.getRegistrosHC().isEmpty())).collect(Collectors.toList());
        List<RegistroHC> registros = organizacionesLocalidad.stream().map(o -> o.devolverUltimoRegistro()).collect(Collectors.toList());
        RegistroHC registroHC = RegistroHC.unificarRegistros(registros);

        organizacionesLocalidad.forEach(o-> System.out.println(o.getRazonSocial()));
        System.out.println(registroHC.getValorHCTotal().getValor());

        provincia.calcularHC();
        System.out.println("Provincia: "+provincia.getNombre()+" "+provincia.calcularHC().getValorHCTotal().getValor());
    }
    @Test
    public void test3(){
        RepositorioLocalidad repositorioLocalidad=new RepositorioLocalidad();
        RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
        RepositorioProvincia repositorioProvincia = new RepositorioProvincia();
        RepositorioSectorTerritorial repositorioSectorTerritorial = new RepositorioSectorTerritorial();

        Provincia provincia = repositorioProvincia.buscarPorId(6);

        Localidad ezeiza = repositorioLocalidad.buscarPorId(Long.parseLong("6270010001"));

        List<Organizacion> organizacionList = repositorioOrganizacion.buscarTodos();
        organizacionList =  organizacionList.stream().filter(o->!(o.getRegistrosHC().isEmpty()) && o.getSectoresTerritoriales().stream().map(s -> s.getId()).collect(Collectors.toList()).contains(repositorioSectorTerritorial.buscar(2).getId())).collect(Collectors.toList());
        organizacionList.forEach(o-> System.out.println(o.getRazonSocial()));
        System.out.println(organizacionList.isEmpty());



    }
}
