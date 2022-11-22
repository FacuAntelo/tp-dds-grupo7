package models.db;

import ServicioGeoRefAPIgob.ServicioGeoRefAPI;
import ServicioGeoRefAPIgob.retrofit.ServicioGeoRefAPIRetrofit;
import models.Combustible.Combustible;
import models.HuellaDeCarbono.CalculadoraHC;
import models.MediosDeTransporte.TipoVehiculo;
import models.MediosDeTransporte.VehiculoParticular;
import models.Usuarios.FactorDeEmision;
import models.Usuarios.Permiso;
import models.Usuarios.Rol;
import models.domain.services.ServicioGeoDDS;
import models.domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import models.trayecto.Provincia;
import repositories.RepositorioCombustible;
import repositories.RepositorioProvincia;
import repositories.RepositorioRol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersistenciaInicial {

    public static void persistirTodoLoNecesario() throws IOException {
        RepositorioProvincia repositorioProvincia = new RepositorioProvincia();
        System.out.println(repositorioProvincia.traerTodas().size()+ "-------------------------------------------------------");
        if(repositorioProvincia.traerTodas().isEmpty()) {
            ServicioGeoRefAPI servicioGeoRefAPI = new ServicioGeoRefAPI();
            servicioGeoRefAPI.setAdapter(new ServicioGeoRefAPIRetrofit());
            List<Provincia> provincias = repositorioProvincia.cargarProvincias();
            repositorioProvincia.persistirProvincias(provincias);
        }
        RepositorioCombustible repositorioCombustible = new RepositorioCombustible();

        FactorDeEmision naftaFactorDeEmision = new FactorDeEmision("NAFTA", 10, "lts");
        Combustible nafta = new Combustible("Nafta");
        nafta.setFactorEmision(naftaFactorDeEmision);

        FactorDeEmision gasFactorDeEmision = new FactorDeEmision("GAS NATURAL", 10, "m3");
        Combustible gas = new Combustible("Gas");
        gas.setFactorEmision(gasFactorDeEmision);

        FactorDeEmision electricidadFactorDeEmision = new FactorDeEmision("ELECTRICIDAD", 50, "kWh");
        Combustible electricidad = new Combustible("Electricidad");
        electricidad.setFactorEmision(electricidadFactorDeEmision);

        FactorDeEmision dieselFactorDeEmision = new FactorDeEmision("DIESEL", 50, "lts");
        Combustible diesel = new Combustible("Diesel");
        diesel.setFactorEmision(dieselFactorDeEmision);

        FactorDeEmision gasoilFactorDeEmision = new FactorDeEmision("GASOIL", 50, "lts");
        Combustible gasoil = new Combustible("Gasoil");
        gasoil.setFactorEmision(gasoilFactorDeEmision);

//        FactorDeEmision keroseneFactorDeEmision = new FactorDeEmision("KEROSENE", 50, "lts");
//        Combustible kerosene = new Combustible("Kerosene");
//        kerosene.setFactorEmision(keroseneFactorDeEmision);
//
//        FactorDeEmision fuelOilFactorDeEmision = new FactorDeEmision("FUEL OIL", 50, "lts");
//        Combustible nafta = new Combustible("Nafta");
//
//        FactorDeEmision carbonFactorDeEmision = new FactorDeEmision("CARBON", 50, "kg");
//        Combustible nafta = new Combustible("Nafta");
//
//        FactorDeEmision carbonLeniaFactorDeEmision = new FactorDeEmision("CARBON LEÑA", 250, "kg");
//        Combustible nafta = new Combustible("Nafta");
//
//        FactorDeEmision leniaFactorDeEmision = new FactorDeEmision("LEÑA", 25, "kg");
//        Combustible nafta = new Combustible("Nafta");

        FactorDeEmision gncFactorDeEmision = new FactorDeEmision("GNC", 25, "lts");
        Combustible gnc = new Combustible("GNC");
        gnc.setFactorEmision(gncFactorDeEmision);

        List<Combustible> combustiblesAPErsistir = new ArrayList<>();
        combustiblesAPErsistir.add(nafta);
        combustiblesAPErsistir.add(gas);
        combustiblesAPErsistir.add(electricidad);
        combustiblesAPErsistir.add(diesel);
        combustiblesAPErsistir.add(gasoil);
        combustiblesAPErsistir.add(gnc);

        List<String> nombreDeCombustiblesPersistidos = repositorioCombustible.buscarTodos().stream().map(c -> c.getNombre()).collect(Collectors.toList());

        combustiblesAPErsistir.forEach(c->{
            if(!nombreDeCombustiblesPersistidos.contains(c.getNombre())){
                repositorioCombustible.guardar(c);
            }
        });

//        //Modificar luego
        List<Combustible> combustiblesPersistidos = repositorioCombustible.buscarTodos().stream()
                .filter(c->c.getNombre()==nafta.getNombre()|| c.getNombre()== gas.getNombre() || c.getNombre()== electricidad.getNombre()|| c.getNombre()== diesel.getNombre())
                .collect(Collectors.toList());
        VehiculoParticular vehiculoParticularAutoCompartido = new VehiculoParticular(TipoVehiculo.AUTO,nafta,true);
        VehiculoParticular vehiculoParticularAutoNoCompartido= new VehiculoParticular(TipoVehiculo.AUTO,nafta,false);
        RepositorioRol repositorioRol = new RepositorioRol();
        if(repositorioRol.buscarTodos().isEmpty()) {
            Rol admin = new Rol();
            admin.setNombre("ADMIN");

            admin.agregarPermiso(Permiso.CREAR_ORGANIZACIONES);
            admin.agregarPermiso(Permiso.VER_ORGANIZACIONES);
            admin.agregarPermiso(Permiso.EDITAR_ORGANIZACIONES);
            admin.agregarPermiso(Permiso.ELIMIAR_ORGANIZACIONES);

            admin.agregarPermiso(Permiso.CREAR_SECTORES);
            admin.agregarPermiso(Permiso.VER_SECTORES);
            admin.agregarPermiso(Permiso.EDITAR_SECTORES);
            admin.agregarPermiso(Permiso.ELIMINAR_SECTORES);

            admin.agregarPermiso(Permiso.VER_PETICIONES);
            admin.agregarPermiso(Permiso.ACEPTAR_PETICIONES);
            admin.agregarPermiso(Permiso.RECHAZAR_PETICIONES);

            admin.agregarPermiso(Permiso.REGISTRAR_MEDICIONES);
            admin.agregarPermiso(Permiso.VER_REPORTES);

            admin.agregarPermiso(Permiso.VER_RECOMENDACIONES);

            admin.agregarPermiso(Permiso.CREAR_TRAYECTOS);
            admin.agregarPermiso(Permiso.VER_TRAYECTOS);
            admin.agregarPermiso(Permiso.EDITAR_TRAYECTOS);
            admin.agregarPermiso(Permiso.ELIMINAR_TRAYECTOS);

            admin.agregarPermiso(Permiso.CALCULAR_HUEYA_CARBONO);

            EntityManagerHelper.beginTransaction();
            EntityManagerHelper.getEntityManager().persist(admin);
            EntityManagerHelper.commit();

            Rol miembro = new Rol();
            miembro.setNombre("USUARIO");


            miembro.agregarPermiso(Permiso.VER_TRAYECTOS);
            miembro.agregarPermiso(Permiso.CREAR_TRAYECTOS);
            miembro.agregarPermiso(Permiso.EDITAR_TRAYECTOS);
            miembro.agregarPermiso(Permiso.ELIMINAR_TRAYECTOS);

            miembro.agregarPermiso(Permiso.VER_ORGANIZACIONES);
            miembro.agregarPermiso(Permiso.CREAR_ORGANIZACIONES);
            miembro.agregarPermiso(Permiso.EDITAR_ORGANIZACIONES);

            miembro.agregarPermiso(Permiso.VER_SECTORES);
            miembro.agregarPermiso(Permiso.CREAR_SECTORES);
            miembro.agregarPermiso(Permiso.EDITAR_SECTORES);
            miembro.agregarPermiso(Permiso.ELIMINAR_SECTORES);

            miembro.agregarPermiso(Permiso.REGISTRAR_MEDICIONES);
            miembro.agregarPermiso(Permiso.CALCULAR_HUEYA_CARBONO);
            miembro.agregarPermiso(Permiso.VER_REPORTES);
            miembro.agregarPermiso(Permiso.VER_RECOMENDACIONES);

            miembro.agregarPermiso(Permiso.VER_PETICIONES);
            miembro.agregarPermiso(Permiso.ACEPTAR_PETICIONES);
            miembro.agregarPermiso(Permiso.RECHAZAR_PETICIONES);

            EntityManagerHelper.beginTransaction();
            EntityManagerHelper.getEntityManager().persist(miembro);
            EntityManagerHelper.commit();
        }
        ServicioGeoDDS servicioGeoDDS = ServicioGeoDDS.getInstance();
        servicioGeoDDS.setAdapter(new ServicioGeoDDSRetrofitAdapter());
        CalculadoraHC calculadoraHC = new CalculadoraHC();
    }

}
