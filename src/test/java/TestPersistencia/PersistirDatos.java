package TestPersistencia;

import ServicioGeoRefAPIgob.ServicioGeoRefAPI;
import ServicioGeoRefAPIgob.retrofit.ServicioGeoRefAPIRetrofit;
import models.AgenteSectorial.SectorTerritorial;
import models.Combustible.Combustible;
import models.HuellaDeCarbono.CalculadoraHC;
import models.MediosDeTransporte.*;
import models.Miembro.Miembro;
import models.Miembro.TipoDocumento;
import models.Organizacion.Clasificacion;
import models.Organizacion.Organizacion;
import models.Organizacion.TipoOrganizacion;
import models.Organizacion.Ubicacion;
import models.Reportes.GeneradorDeReportes;
import models.Sector.Sector;
import models.Usuarios.FactorDeEmision;
import models.db.EntityManagerHelper;
import models.db.PersistenciaInicial;
import models.domain.Configurador;
import models.domain.services.ServicioGeoDDS;
import models.domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import models.trayecto.*;
import models.unidad.GR;
import models.unidad.KG;
import models.unidad.TN;
import org.junit.Test;
import repositories.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static models.MediosDeTransporte.TipoVehiculo.AUTO;
import static models.MediosDeTransporte.TipoVehiculo.MOTO;
import static models.Miembro.TipoDocumento.DNI;
import static models.Organizacion.TipoOrganizacion.EMPRESA;

public class PersistirDatos {
    RepositorioProvincia repositorioProvincia = new RepositorioProvincia();
    RepositorioLocalidad repositorioLocalidad = new RepositorioLocalidad();
    RepositorioCombustible repositorioCombustible= new RepositorioCombustible();
    RepositorioOrganizacion repositorioOrganizacion = new RepositorioOrganizacion();
    RepositorioMiembro repositorioMiembro = new RepositorioMiembro();

    @Test
    public void persistirDatosEjemplo() throws IOException {
        this.persisitrProvinciasYLocalidades();
        PersistenciaInicial.persistirCombustibles();
        Organizacion cocaCola = this.getOrganizacion();
        Organizacion jumbo = this.getOrganizacion2();
        repositorioOrganizacion.guardar(cocaCola, jumbo);
    }

    public void persisitrProvinciasYLocalidades() throws IOException {

        System.out.println(repositorioProvincia.traerTodas().size()+ "-------------------------------------------------------");
        if(repositorioProvincia.traerTodas().isEmpty()) {
            ServicioGeoRefAPI servicioGeoRefAPI = new ServicioGeoRefAPI();
            servicioGeoRefAPI.setAdapter(new ServicioGeoRefAPIRetrofit());
            List<Provincia> provincias = repositorioProvincia.cargarProvincias();
            repositorioProvincia.persistirProvincias(provincias);
        }
    }

    public Organizacion getOrganizacion() throws IOException {
        String path = new String("src\\main\\java\\models\\Libro1.xlsx");

        ServicioGeoDDS servicioGeoDDS = ServicioGeoDDS.getInstance();
        servicioGeoDDS.setAdapter(new ServicioGeoDDSRetrofitAdapter());
        CalculadoraHC calculadoraHC = new CalculadoraHC();

        SectorTerritorial sectorBonaerense = new SectorTerritorial();


        KG kg = KG.getKG();
        TN tn = TN.getTN();
        GR gr = GR.getGR();
//        FactorDeEmision naftaFactorDeEmision = new FactorDeEmision("NAFTA", 10, "lts");
//        Combustible nafta = new Combustible("nafta");
//        nafta.setFactorEmision(naftaFactorDeEmision);
//        FactorDeEmision gasFactorDeEmision = new FactorDeEmision("GAS NATURAL", 10, "m3");
//        Combustible gas = new Combustible("gas");
//        gas.setFactorEmision(gasFactorDeEmision);
//        FactorDeEmision electricidadFactorDeEmision = new FactorDeEmision("ELECTRICIDAD", 50, "kWh");
//        Combustible electricidad = new Combustible("electricidad");
//        electricidad.setFactorEmision(electricidadFactorDeEmision);
//
//        FactorDeEmision dieselFactorDeEmision = new FactorDeEmision("DIESEL", 50, "lts");
//        Combustible diesel = new Combustible("diesel");
//        diesel.setFactorEmision(dieselFactorDeEmision);
//        FactorDeEmision gasoilFactorDeEmision = new FactorDeEmision("GASOIL", 50, "lts");
//        FactorDeEmision keroseneFactorDeEmision = new FactorDeEmision("KEROSENE", 50, "lts");
//        FactorDeEmision fuelOilFactorDeEmision = new FactorDeEmision("FUEL OIL", 50, "lts");
//        FactorDeEmision carbonFactorDeEmision = new FactorDeEmision("CARBON", 50, "kg");
//        FactorDeEmision carbonLeniaFactorDeEmision = new FactorDeEmision("CARBON LEÑA", 250, "kg");
//        FactorDeEmision leniaFactorDeEmision = new FactorDeEmision("LEÑA", 25, "kg");
//        FactorDeEmision gncFactorDeEmision = new FactorDeEmision("GNC", 25, "lts");
        Configurador config = Configurador.getConfigurador();
        System.out.println();
        SectorTerritorial sectorTerritorial = new SectorTerritorial();
        // ORGANIZACION //
        // UBICACION //
        Ubicacion ubicacionCocaCola = new Ubicacion();
        // DIRECCION //
        Localidad ezeiza = repositorioLocalidad.buscarPorId(Long.parseLong("6270010001"));
        Provincia buenosAires = repositorioProvincia.buscarPorId(6);
        sectorBonaerense.agregarTerritorio(ezeiza);
        sectorBonaerense.agregarTerritorio(buenosAires);
        Direccion direccionCocaCola = new Direccion("Medrano", 1500, ezeiza, buenosAires);
        // FIN DIRECCION
        ubicacionCocaCola.setCodigoPostal(1804);
        ubicacionCocaCola.setDireccion(direccionCocaCola);

        sectorTerritorial.agregarTerritorio(ezeiza);
        sectorTerritorial.agregarTerritorio(buenosAires);
        // FIN UBICACION //
        // TIPO DE LA ORGANIZACION //
        TipoOrganizacion tipoEmpresa = TipoOrganizacion.EMPRESA;
        // FIN TIPO DE LA ORGANIZACION //
        // CLASIFICACION //
        Clasificacion clasificacionProductor = new Clasificacion();
        clasificacionProductor.setNombre("Productor");
        // FIN CLASIFICACION //
        Organizacion cocaCola = new Organizacion("Coca Cola Company", tipoEmpresa, clasificacionProductor, ubicacionCocaCola);
        // FIN ORGANIZACION //
        // SECTOR //
        Sector ventasCocaCola = new Sector("Ventas", cocaCola);
        cocaCola.agregarSector(ventasCocaCola);

        Sector administracionCocaCola = new Sector("Administracion", cocaCola);
        cocaCola.agregarSector(administracionCocaCola);

        Sector marketingCocaCola = new Sector("Marketing", cocaCola);
        cocaCola.agregarSector(marketingCocaCola);
        // FIN SECTORES //

        // MIEMBROS //
        Miembro oscarAdministracion = new Miembro("Oscar", "Longaniza", TipoDocumento.DNI, "17545848");
        administracionCocaCola.agregarMiembro(oscarAdministracion);

        Miembro vanesaMarketing = new Miembro("Vanesa", "lartori", TipoDocumento.DNI, "4564564");
        marketingCocaCola.agregarMiembro(vanesaMarketing);

        Miembro edgardoVentas = new Miembro("Edgardo", "Rosales", TipoDocumento.DNI, "468465");
        ventasCocaCola.agregarMiembro(edgardoVentas);

        Miembro teresaMarketing = new Miembro("Teresa", "Menta granizada", TipoDocumento.DNI, "4565465");
        marketingCocaCola.agregarMiembro(teresaMarketing);

        // FIN MIEMBROS //

        // DIRECCIONES Y TRAYECTOS DE LOS MIEMBROS //
        Direccion direccionCasaOscar = new Direccion("COD Warzone", 2, ezeiza, buenosAires);
        Trayecto trayectoOscar = new Trayecto(direccionCasaOscar, direccionCocaCola);
        oscarAdministracion.agregarTrayecto(trayectoOscar, cocaCola);

        Direccion direccionCasaVanesa = new Direccion("LOL", 20020, ezeiza, buenosAires);
        Trayecto trayectoVanesa = new Trayecto(direccionCasaVanesa, direccionCocaCola);
        vanesaMarketing.agregarTrayecto(trayectoVanesa, cocaCola);

        Direccion direccionCasaEdgardo = new Direccion("CS:GO", 205, ezeiza, buenosAires);
        Trayecto trayectoEdgardo = new Trayecto(direccionCasaEdgardo, direccionCocaCola);
        edgardoVentas.agregarTrayecto(trayectoEdgardo, cocaCola);

        Direccion direccionCasaTeresa = new Direccion("outlast", 101, ezeiza, buenosAires);
        Trayecto trayectoTeresa = new Trayecto(direccionCasaTeresa, direccionCocaCola);
        teresaMarketing.agregarTrayecto(trayectoTeresa, cocaCola);
        // FIN DIRECCIONES Y TRAYECTOS DE LOS MIEMBROS
        // TRAMOS Y MEDIOS DE TRANSPORTE
        MediosSinContaminar pie = new MediosSinContaminar();
        //COMBUSTIBLE Y FACTOR DE EMISIONES


        // SERVICIOS
        Servicio servicioUber = new Servicio("Uber");
        MediosDeTransporte uber = new ServicioContratado(servicioUber, false);
        Combustible gas = repositorioCombustible.buscarPorId(2);
        Combustible nafta = repositorioCombustible.buscarPorId(1);
        uber.setCombustible(gas);
        VehiculoParticular motoCompartidaNafta = new VehiculoParticular(TipoVehiculo.MOTO, nafta, true);
        VehiculoParticular autoSolitarioGas = new VehiculoParticular(TipoVehiculo.AUTO, gas, false);

        //EL TRAYECTO DE VANESA
        //DE SU CASA AL GARAJE
        Direccion direccionGarageVanesa = new Direccion("SUPER GARAJE", 8550, ezeiza, buenosAires);
        LocalTime horaVanesaAlGarage = LocalTime.of(8, 30);
        Tramo tramoVanesaAlGarage = new Tramo(direccionCasaTeresa, direccionGarageVanesa, horaVanesaAlGarage);
        tramoVanesaAlGarage.setMedioDeTransporte(pie);
        trayectoVanesa.agregarTramo(tramoVanesaAlGarage);
        // DEL GARAJE A LA COCA COLA
        LocalTime horaVanesaACocaCola = LocalTime.of(9, 0);
        Tramo tramoVanesaACocaCola = new Tramo(direccionGarageVanesa, direccionCocaCola, horaVanesaACocaCola);
        tramoVanesaACocaCola.setMedioDeTransporte(motoCompartidaNafta);
        trayectoVanesa.agregarTramo(tramoVanesaACocaCola);
        //EL TRAYECTO DE EDGARDO
        // DE SU CASA AL GARAJE
        LocalTime horaGarageEdgardo = LocalTime.of(8, 45);
        Direccion direccionGarageEdgardo = new Direccion("SUPER GARAJE", 8550, ezeiza, buenosAires);
        Tramo tramoEdgardoAlGaraje = new Tramo(direccionCasaEdgardo, direccionGarageEdgardo, horaGarageEdgardo);
        tramoEdgardoAlGaraje.setMedioDeTransporte(pie);
        trayectoEdgardo.agregarTramo(tramoEdgardoAlGaraje);
        // DEL GARAJE A COCA COLA
        LocalTime horaEdgardoACocaCola = LocalTime.of(9, 0);
        Tramo tramoEdgardoACocaCola = new Tramo(direccionGarageEdgardo, direccionCocaCola, horaEdgardoACocaCola);
        tramoEdgardoACocaCola.setMedioDeTransporte(motoCompartidaNafta);
        trayectoEdgardo.agregarTramo(tramoEdgardoACocaCola);
        // EL TRAYECTO DE TERESA
        LocalTime horaTeresaACocaCola = LocalTime.of(9, 15);
        Tramo tramoTeresaACocaCola = new Tramo(direccionCasaTeresa, direccionCocaCola, horaTeresaACocaCola);
        tramoTeresaACocaCola.setMedioDeTransporte(uber);
        trayectoTeresa.agregarTramo(tramoTeresaACocaCola);
        // EL TRAYECTO DE OSCAR
        LocalTime horaOscarACocaCola = LocalTime.of(15, 50);
        Tramo tramoOscarACocaCola = new Tramo(direccionCasaOscar, direccionCocaCola, horaOscarACocaCola);
        tramoOscarACocaCola.setMedioDeTransporte(autoSolitarioGas);
        trayectoOscar.agregarTramo(tramoOscarACocaCola);

        List<Tramo> tramosMiembros = cocaCola.obtenerTramosDeLosMiembros();

        cocaCola.leerExcel(path);
        return cocaCola;
    }

    public Organizacion getOrganizacion2() throws IOException {
        String path = new String("src\\main\\java\\models\\Libro1.xlsx");

        ServicioGeoDDS servicioGeoDDS = ServicioGeoDDS.getInstance();
        servicioGeoDDS.setAdapter(new ServicioGeoDDSRetrofitAdapter());
        CalculadoraHC calculadoraHC = new CalculadoraHC();

        SectorTerritorial sectorBonaerense = new SectorTerritorial();


        KG kg = KG.getKG();
        TN tn = TN.getTN();
        GR gr = GR.getGR();
//        FactorDeEmision naftaFactorDeEmision = new FactorDeEmision("NAFTA", 10, "lts");
//        Combustible nafta = new Combustible("nafta");
//        nafta.setFactorEmision(naftaFactorDeEmision);
//        FactorDeEmision gasFactorDeEmision = new FactorDeEmision("GAS NATURAL", 10, "m3");
//        Combustible gas = new Combustible("gas");
//        gas.setFactorEmision(gasFactorDeEmision);
//        FactorDeEmision electricidadFactorDeEmision = new FactorDeEmision("ELECTRICIDAD", 50, "kWh");
//        Combustible electricidad = new Combustible("electricidad");
//        electricidad.setFactorEmision(electricidadFactorDeEmision);
//
//        FactorDeEmision dieselFactorDeEmision = new FactorDeEmision("DIESEL", 50, "lts");
//        Combustible diesel = new Combustible("diesel");
//        diesel.setFactorEmision(dieselFactorDeEmision);
//        FactorDeEmision gasoilFactorDeEmision = new FactorDeEmision("GASOIL", 50, "lts");
//        FactorDeEmision keroseneFactorDeEmision = new FactorDeEmision("KEROSENE", 50, "lts");
//        FactorDeEmision fuelOilFactorDeEmision = new FactorDeEmision("FUEL OIL", 50, "lts");
//        FactorDeEmision carbonFactorDeEmision = new FactorDeEmision("CARBON", 50, "kg");
//        FactorDeEmision carbonLeniaFactorDeEmision = new FactorDeEmision("CARBON LEÑA", 250, "kg");
//        FactorDeEmision leniaFactorDeEmision = new FactorDeEmision("LEÑA", 25, "kg");
//        FactorDeEmision gncFactorDeEmision = new FactorDeEmision("GNC", 25, "lts");
        Configurador config = Configurador.getConfigurador();
        System.out.println();
        SectorTerritorial sectorTerritorial = new SectorTerritorial();
        // ORGANIZACION //
        // UBICACION //
        Ubicacion ubicacionCocaCola = new Ubicacion();
        // DIRECCION //
        Localidad ezeiza = repositorioLocalidad.buscarPorId(Long.parseLong("6270010001"));
        Provincia buenosAires = repositorioProvincia.buscarPorId(6);
        sectorBonaerense.agregarTerritorio(ezeiza);
        sectorBonaerense.agregarTerritorio(buenosAires);
        Direccion direccionCocaCola = new Direccion("Medrano", 1500, ezeiza, buenosAires);
        // FIN DIRECCION
        ubicacionCocaCola.setCodigoPostal(1804);
        ubicacionCocaCola.setDireccion(direccionCocaCola);

        sectorTerritorial.agregarTerritorio(ezeiza);
        sectorTerritorial.agregarTerritorio(buenosAires);
        // FIN UBICACION //
        // TIPO DE LA ORGANIZACION //
        TipoOrganizacion tipoEmpresa = TipoOrganizacion.EMPRESA;
        // FIN TIPO DE LA ORGANIZACION //
        // CLASIFICACION //
        Clasificacion clasificacionProductor = new Clasificacion();
        clasificacionProductor.setNombre("Productor");
        // FIN CLASIFICACION //
        Organizacion cocaCola = new Organizacion("Jumbo", tipoEmpresa, clasificacionProductor, ubicacionCocaCola);
        // FIN ORGANIZACION //
        // SECTOR //
        Sector ventasCocaCola = new Sector("Contabilidad", cocaCola);
        cocaCola.agregarSector(ventasCocaCola);

        Sector administracionCocaCola = new Sector("Producción ", cocaCola);
        cocaCola.agregarSector(administracionCocaCola);

        Sector marketingCocaCola = new Sector("Recursos Humanos", cocaCola);
        cocaCola.agregarSector(marketingCocaCola);
        // FIN SECTORES //

        // MIEMBROS //
        Miembro oscarAdministracion = new Miembro("Lucas", "Frutilla", TipoDocumento.DNI, "643546846");
        administracionCocaCola.agregarMiembro(oscarAdministracion);

        Miembro vanesaMarketing = new Miembro("Leo", "Naranja", TipoDocumento.DNI, "0505878");
        marketingCocaCola.agregarMiembro(vanesaMarketing);

        Miembro edgardoVentas = new Miembro("Lucía", "Anana", TipoDocumento.DNI, "065848");
        ventasCocaCola.agregarMiembro(edgardoVentas);

        Miembro teresaMarketing = new Miembro("Sofía", "Mandarina", TipoDocumento.DNI, "635107");
        marketingCocaCola.agregarMiembro(teresaMarketing);



        // FIN MIEMBROS //

        // DIRECCIONES Y TRAYECTOS DE LOS MIEMBROS //
        Direccion direccionCasaOscar = new Direccion("COD Warzone", 2, ezeiza, buenosAires);
        Trayecto trayectoOscar = new Trayecto(direccionCasaOscar, direccionCocaCola);
        oscarAdministracion.agregarTrayecto(trayectoOscar, cocaCola);

        Direccion direccionCasaVanesa = new Direccion("LOL", 20020, ezeiza, buenosAires);
        Trayecto trayectoVanesa = new Trayecto(direccionCasaVanesa, direccionCocaCola);
        vanesaMarketing.agregarTrayecto(trayectoVanesa, cocaCola);

        Direccion direccionCasaEdgardo = new Direccion("CS:GO", 205, ezeiza, buenosAires);
        Trayecto trayectoEdgardo = new Trayecto(direccionCasaEdgardo, direccionCocaCola);
        edgardoVentas.agregarTrayecto(trayectoEdgardo, cocaCola);

        Direccion direccionCasaTeresa = new Direccion("outlast", 101, ezeiza, buenosAires);
        Trayecto trayectoTeresa = new Trayecto(direccionCasaTeresa, direccionCocaCola);
        teresaMarketing.agregarTrayecto(trayectoTeresa, cocaCola);
        // FIN DIRECCIONES Y TRAYECTOS DE LOS MIEMBROS
        // TRAMOS Y MEDIOS DE TRANSPORTE
        MediosSinContaminar pie = new MediosSinContaminar();
        //COMBUSTIBLE Y FACTOR DE EMISIONES


        // SERVICIOS
        Servicio servicioUber = new Servicio("Uber");
        MediosDeTransporte uber = new ServicioContratado(servicioUber, false);
        Combustible gas = repositorioCombustible.buscarPorId(2);
        Combustible nafta = repositorioCombustible.buscarPorId(1);
        uber.setCombustible(gas);
        VehiculoParticular motoCompartidaNafta = new VehiculoParticular(TipoVehiculo.MOTO, nafta, true);
        VehiculoParticular autoSolitarioGas = new VehiculoParticular(TipoVehiculo.AUTO, gas, false);

        //EL TRAYECTO DE VANESA
        //DE SU CASA AL GARAJE
        Direccion direccionGarageVanesa = new Direccion("SUPER GARAJE", 8550, ezeiza, buenosAires);
        LocalTime horaVanesaAlGarage = LocalTime.of(8, 30);
        Tramo tramoVanesaAlGarage = new Tramo(direccionCasaTeresa, direccionGarageVanesa, horaVanesaAlGarage);
        tramoVanesaAlGarage.setMedioDeTransporte(pie);
        trayectoVanesa.agregarTramo(tramoVanesaAlGarage);
        // DEL GARAJE A LA COCA COLA
        LocalTime horaVanesaACocaCola = LocalTime.of(9, 0);
        Tramo tramoVanesaACocaCola = new Tramo(direccionGarageVanesa, direccionCocaCola, horaVanesaACocaCola);
        tramoVanesaACocaCola.setMedioDeTransporte(motoCompartidaNafta);
        trayectoVanesa.agregarTramo(tramoVanesaACocaCola);
        //EL TRAYECTO DE EDGARDO
        // DE SU CASA AL GARAJE
        LocalTime horaGarageEdgardo = LocalTime.of(8, 45);
        Direccion direccionGarageEdgardo = new Direccion("SUPER GARAJE", 8550, ezeiza, buenosAires);
        Tramo tramoEdgardoAlGaraje = new Tramo(direccionCasaEdgardo, direccionGarageEdgardo, horaGarageEdgardo);
        tramoEdgardoAlGaraje.setMedioDeTransporte(pie);
        trayectoEdgardo.agregarTramo(tramoEdgardoAlGaraje);
        // DEL GARAJE A COCA COLA
        LocalTime horaEdgardoACocaCola = LocalTime.of(9, 0);
        Tramo tramoEdgardoACocaCola = new Tramo(direccionGarageEdgardo, direccionCocaCola, horaEdgardoACocaCola);
        tramoEdgardoACocaCola.setMedioDeTransporte(motoCompartidaNafta);
        trayectoEdgardo.agregarTramo(tramoEdgardoACocaCola);
        // EL TRAYECTO DE TERESA
        LocalTime horaTeresaACocaCola = LocalTime.of(9, 15);
        Tramo tramoTeresaACocaCola = new Tramo(direccionCasaTeresa, direccionCocaCola, horaTeresaACocaCola);
        tramoTeresaACocaCola.setMedioDeTransporte(uber);
        trayectoTeresa.agregarTramo(tramoTeresaACocaCola);
        // EL TRAYECTO DE OSCAR
        LocalTime horaOscarACocaCola = LocalTime.of(15, 50);
        Tramo tramoOscarACocaCola = new Tramo(direccionCasaOscar, direccionCocaCola, horaOscarACocaCola);
        tramoOscarACocaCola.setMedioDeTransporte(autoSolitarioGas);
        trayectoOscar.agregarTramo(tramoOscarACocaCola);

        List<Tramo> tramosMiembros = cocaCola.obtenerTramosDeLosMiembros();

        cocaCola.leerExcel(path);
        return cocaCola;
    }

}
