package TestPersistencia;

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
import models.Sector.Sector;
import models.Usuarios.FactorDeEmision;
import models.domain.Configurador;
import models.domain.services.ServicioGeoDDS;
import models.domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import models.trayecto.*;
import models.unidad.GR;
import models.unidad.KG;
import models.unidad.TN;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repositories.RepositorioOrganizacion;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class TestPersistenciaOrganizacion {

    RepositorioOrganizacion repo;
    FactorDeEmision naftaFactorDeEmision ;
    Combustible nafta ;
    FactorDeEmision gasFactorDeEmision ;
    Combustible gas = new Combustible("gas");
    FactorDeEmision electricidadFactorDeEmision ;
    Combustible electricidad = new Combustible("electricidad");
    FactorDeEmision dieselFactorDeEmision ;
    Combustible diesel = new Combustible("diesel");

    @Before
    public void init(){

        repo = new RepositorioOrganizacion();

        naftaFactorDeEmision = new FactorDeEmision("NAFTA", 10, "lts");
        nafta = new Combustible("nafta");
        nafta.setFactorEmision(naftaFactorDeEmision);
        gasFactorDeEmision = new FactorDeEmision("GAS NATURAL", 10, "m3");
        gas = new Combustible("gas");
        gas.setFactorEmision(gasFactorDeEmision);
        electricidadFactorDeEmision = new FactorDeEmision("ELECTRICIDAD", 50, "kWh");
        electricidad = new Combustible("electricidad");
        electricidad.setFactorEmision(electricidadFactorDeEmision);
        dieselFactorDeEmision = new FactorDeEmision("DIESEL", 50, "lts");
        diesel = new Combustible("diesel");
        diesel.setFactorEmision(dieselFactorDeEmision);

    }


    @Test
    public void persistirOrganizacion() throws IOException {
        init();
        Organizacion cocaCola = this.getOrganizacion();
        Organizacion jumbo = this.getOrganizacion2();
        repo.guardar(cocaCola);
        repo.guardar(jumbo);
    }
    
    @Test
    public void testRecuperarMiembros(){
        Integer idCocacola = 1;
        List<Miembro> miembros = repo.buscarTodosLosMiembros(idCocacola);
        miembros.forEach(m->System.out.println(m.getNombre() + m.getApellido()));

        Assert.assertEquals(4,miembros.size());


    }

    @Test
    public void recuperarOrganizacionConIdUsuario(){
        Integer idUsuario = 1;
        List<Organizacion> organizaciones = repo.buscarOrganizacionesDelUsuarioQueEsMiembro(idUsuario);
        Assert.assertEquals(2,organizaciones.size());
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
        FactorDeEmision gasoilFactorDeEmision = new FactorDeEmision("GASOIL", 50, "lts");
        FactorDeEmision keroseneFactorDeEmision = new FactorDeEmision("KEROSENE", 50, "lts");
        FactorDeEmision fuelOilFactorDeEmision = new FactorDeEmision("FUEL OIL", 50, "lts");
        FactorDeEmision carbonFactorDeEmision = new FactorDeEmision("CARBON", 50, "kg");
        FactorDeEmision carbonLeniaFactorDeEmision = new FactorDeEmision("CARBON LE??A", 250, "kg");
        FactorDeEmision leniaFactorDeEmision = new FactorDeEmision("LE??A", 25, "kg");
        FactorDeEmision gncFactorDeEmision = new FactorDeEmision("GNC", 25, "lts");
        Configurador config = Configurador.getConfigurador();
        System.out.println();
        SectorTerritorial sectorTerritorial = new SectorTerritorial();
        // ORGANIZACION //
        // UBICACION //
        Ubicacion ubicacionCocaCola = new Ubicacion();
        // DIRECCION //
        Localidad ezeiza = new Localidad();
        Provincia buenosAires = new Provincia("Buenos Aires");
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
        FactorDeEmision naftaFactorDeEmision = new FactorDeEmision("NAFTA", 10, "lts");
        Combustible nafta = new Combustible("nafta");
        nafta.setFactorEmision(naftaFactorDeEmision);
        FactorDeEmision gasFactorDeEmision = new FactorDeEmision("GAS NATURAL", 10, "m3");
        Combustible gas = new Combustible("gas");
        gas.setFactorEmision(gasFactorDeEmision);
        FactorDeEmision electricidadFactorDeEmision = new FactorDeEmision("ELECTRICIDAD", 50, "kWh");
        Combustible electricidad = new Combustible("electricidad");
        electricidad.setFactorEmision(electricidadFactorDeEmision);

        FactorDeEmision dieselFactorDeEmision = new FactorDeEmision("DIESEL", 50, "lts");
        Combustible diesel = new Combustible("diesel");
        diesel.setFactorEmision(dieselFactorDeEmision);
        FactorDeEmision gasoilFactorDeEmision = new FactorDeEmision("GASOIL", 50, "lts");
        FactorDeEmision keroseneFactorDeEmision = new FactorDeEmision("KEROSENE", 50, "lts");
        FactorDeEmision fuelOilFactorDeEmision = new FactorDeEmision("FUEL OIL", 50, "lts");
        FactorDeEmision carbonFactorDeEmision = new FactorDeEmision("CARBON", 50, "kg");
        FactorDeEmision carbonLeniaFactorDeEmision = new FactorDeEmision("CARBON LE??A", 250, "kg");
        FactorDeEmision leniaFactorDeEmision = new FactorDeEmision("LE??A", 25, "kg");
        FactorDeEmision gncFactorDeEmision = new FactorDeEmision("GNC", 25, "lts");
        Configurador config = Configurador.getConfigurador();
        System.out.println();
        SectorTerritorial sectorTerritorial = new SectorTerritorial();
        // ORGANIZACION //
        // UBICACION //
        Ubicacion ubicacionCocaCola = new Ubicacion();
        // DIRECCION //
        Localidad ezeiza = new Localidad();
        Provincia buenosAires = new Provincia("Buenos Aires");
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

        Sector administracionCocaCola = new Sector("Producci??n ", cocaCola);
        cocaCola.agregarSector(administracionCocaCola);

        Sector marketingCocaCola = new Sector("Recursos Humanos", cocaCola);
        cocaCola.agregarSector(marketingCocaCola);
        // FIN SECTORES //

        // MIEMBROS //
        Miembro oscarAdministracion = new Miembro("Lucas", "Frutilla", TipoDocumento.DNI, "643546846");
        administracionCocaCola.agregarMiembro(oscarAdministracion);

        Miembro vanesaMarketing = new Miembro("Leo", "Naranja", TipoDocumento.DNI, "0505878");
        marketingCocaCola.agregarMiembro(vanesaMarketing);

        Miembro edgardoVentas = new Miembro("Luc??a", "Anana", TipoDocumento.DNI, "065848");
        ventasCocaCola.agregarMiembro(edgardoVentas);

        Miembro teresaMarketing = new Miembro("Sof??a", "Mandarina", TipoDocumento.DNI, "635107");
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
