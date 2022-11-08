import ServicioGeoRefAPIgob.ServicioGeoRefAPI;
import ServicioGeoRefAPIgob.retrofit.ServicioGeoRefAPIRetrofit;
import models.AgenteSectorial.AgenteSectorial;
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
import models.Usuarios.*;
import models.db.EntityManagerHelper;
import models.db.PersistenciaInicial;
import models.domain.Configurador;
import models.domain.services.ServicioGeoDDS;
import models.domain.services.adapters.ServicioGeoDDSRetrofitAdapter;
import models.trayecto.*;
import models.unidad.GR;
import models.unidad.KG;
import models.unidad.TN;
import org.junit.Before;
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
    RepositorioRol repositorioRol = new RepositorioRol();
    RepositorioAgenteSectorial repositorioAgenteSectorial= new RepositorioAgenteSectorial();
    RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

    @Before
    public void init() throws IOException {
        persisitrProvinciasYLocalidades();
        PersistenciaInicial.persistirCombustibles();
        persistirRolAdmin();
        persistirRolMiembro();
        persistirRolOrganizacion();
        persistirRolAgenteSectorial();
    }
    @Test
    public void persistirDatosEjemplo() throws IOException {

        Organizacion cocaCola = this.getOrganizacionCocaCola();
        Organizacion jumbo = this.getOrganizacionJumbo();
        repositorioOrganizacion.guardar(cocaCola, jumbo);

        agregarSectorTerritorialYAgenteSectorial();

        crearAdministrador();
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

    public void persistirRolAdmin(){
        Rol admin = new Rol();
        admin.setNombre("Admin");

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
    }


    public void persistirRolMiembro(){
        Rol miembro = new Rol();
        miembro.setNombre("Miembro");

        miembro.agregarPermiso(Permiso.CALCULAR_HUEYA_CARBONO);
        miembro.agregarPermiso(Permiso.VER_TRAYECTOS);
        miembro.agregarPermiso(Permiso.CREAR_TRAYECTOS);
        miembro.agregarPermiso(Permiso.EDITAR_TRAYECTOS);
        miembro.agregarPermiso(Permiso.ELIMINAR_TRAYECTOS);
        miembro.agregarPermiso(Permiso.VER_ORGANIZACIONES);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(miembro);
        EntityManagerHelper.commit();
    }

    public void persistirRolOrganizacion(){
        Rol organizacion = new Rol();
        organizacion.setNombre("Organizacion");

        organizacion.agregarPermiso(Permiso.CALCULAR_HUEYA_CARBONO);
        organizacion.agregarPermiso(Permiso.VER_ORGANIZACIONES);
        organizacion.agregarPermiso(Permiso.CREAR_ORGANIZACIONES);
        organizacion.agregarPermiso(Permiso.EDITAR_ORGANIZACIONES);

        organizacion.agregarPermiso(Permiso.VER_SECTORES);
        organizacion.agregarPermiso(Permiso.CREAR_SECTORES);
        organizacion.agregarPermiso(Permiso.EDITAR_SECTORES);
        organizacion.agregarPermiso(Permiso.ELIMINAR_SECTORES);

        organizacion.agregarPermiso(Permiso.REGISTRAR_MEDICIONES);
        organizacion.agregarPermiso(Permiso.CALCULAR_HUEYA_CARBONO);
        organizacion.agregarPermiso(Permiso.VER_REPORTES);
        organizacion.agregarPermiso(Permiso.VER_RECOMENDACIONES);

        organizacion.agregarPermiso(Permiso.VER_PETICIONES);
        organizacion.agregarPermiso(Permiso.ACEPTAR_PETICIONES);
        organizacion.agregarPermiso(Permiso.RECHAZAR_PETICIONES);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(organizacion);
        EntityManagerHelper.commit();
    }

    public void persistirRolAgenteSectorial(){
        Rol agenteSectorial = new Rol();
        agenteSectorial.setNombre("AgenteSectorial");

        agenteSectorial.agregarPermiso(Permiso.VER_RECOMENDACIONES);
        agenteSectorial.agregarPermiso(Permiso.VER_REPORTES);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(agenteSectorial);
        EntityManagerHelper.commit();
    }

    public Organizacion getOrganizacionCocaCola() throws IOException {
        String path = new String("src\\main\\java\\models\\Libro1.xlsx");

        ServicioGeoDDS servicioGeoDDS = ServicioGeoDDS.getInstance();
        servicioGeoDDS.setAdapter(new ServicioGeoDDSRetrofitAdapter());
        CalculadoraHC calculadoraHC = new CalculadoraHC();

        SectorTerritorial sectorBonaerense = new SectorTerritorial();


        KG kg = KG.getKG();
        TN tn = TN.getTN();
        GR gr = GR.getGR();

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
        Rol rol_miembro = repositorioRol.buscar(2);
        Miembro oscarAdministracion = new Miembro("Oscar", "Longaniza", TipoDocumento.DNI, "17545848");
        Usuario usuariooscarAdministracion= new Usuario(oscarAdministracion.getNombre(),
                oscarAdministracion.getApellido(),
                oscarAdministracion.getTipoDocumento(),
                oscarAdministracion.getNumDoc(),
                "OscarLonganiza",
                "OscarLonganiza",
                "OscarLonganiza@gmail.com",
                rol_miembro);
        oscarAdministracion.setUsuario(usuariooscarAdministracion);
        administracionCocaCola.agregarMiembro(oscarAdministracion);

        Miembro vanesaMarketing = new Miembro("Vanesa", "lartori", TipoDocumento.DNI, "4564564");
        Usuario usuariovanesaMarketing= new Usuario(vanesaMarketing.getNombre(),
                vanesaMarketing.getApellido(),
                vanesaMarketing.getTipoDocumento(),
                oscarAdministracion.getNumDoc(),
                "Vanesalartori",
                "Vanesalartori",
                "Vanesalartori@gmail.com",
                rol_miembro);
        vanesaMarketing.setUsuario(usuariovanesaMarketing);
        marketingCocaCola.agregarMiembro(vanesaMarketing);

        Miembro edgardoVentas = new Miembro("Edgardo", "Rosales", TipoDocumento.DNI, "468465");
        Usuario usuarioedgardoVentas= new Usuario(edgardoVentas.getNombre(),
                edgardoVentas.getApellido(),
                edgardoVentas.getTipoDocumento(),
                edgardoVentas.getNumDoc(),
                "EdgardoRosales",
                "EdgardoRosales",
                "EdgardoRosales@gmail.com",
                rol_miembro);
        edgardoVentas.setUsuario(usuarioedgardoVentas);
        ventasCocaCola.agregarMiembro(edgardoVentas);

        Miembro teresaMarketing = new Miembro("Teresa", "Menta granizada", TipoDocumento.DNI, "4565465");
        Usuario usuarioteresaMarketing= new Usuario(teresaMarketing.getNombre(),
                teresaMarketing.getApellido(),
                teresaMarketing.getTipoDocumento(),
                teresaMarketing.getNumDoc(),
                "TeresaMentaGranizada",
                "TeresaMentaGranizada",
                "TeresaMentaGranizada@gmail.com",
                rol_miembro);
        teresaMarketing.setUsuario(usuarioedgardoVentas);
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

        //USUARIO DE LA ORGANIZACION
        Usuario usuarioCocacola = new Usuario("CocaCola",
                "CocaCola",
                TipoDocumento.PASAPORTE,
                "123456",
                "CocaCola",
                "CocaCola",
                "CocaCola@gmail.cocacola.com",
                repositorioRol.buscar(3));
        cocaCola.setUsuario(usuarioCocacola);
        return cocaCola;
    }

    public Organizacion getOrganizacionJumbo() throws IOException {
        String path = new String("src\\main\\java\\models\\Libro1.xlsx");

        ServicioGeoDDS servicioGeoDDS = ServicioGeoDDS.getInstance();
        servicioGeoDDS.setAdapter(new ServicioGeoDDSRetrofitAdapter());
        CalculadoraHC calculadoraHC = new CalculadoraHC();

        SectorTerritorial sectorBonaerense = new SectorTerritorial();


        KG kg = KG.getKG();
        TN tn = TN.getTN();
        GR gr = GR.getGR();

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
        Rol rol_miembro = repositorioRol.buscar(2);
        Miembro oscarAdministracion = new Miembro("Lucas", "Frutilla", TipoDocumento.DNI, "643546846");
        Usuario usuariooscarAdministracion= new Usuario(oscarAdministracion.getNombre(),
                oscarAdministracion.getApellido(),
                oscarAdministracion.getTipoDocumento(),
                oscarAdministracion.getNumDoc(),
                "LucasFrutilla",
                "LucasFrutilla",
                "LucasFrutilla@gmail.com",
                rol_miembro);
        oscarAdministracion.setUsuario(usuariooscarAdministracion);
        administracionCocaCola.agregarMiembro(oscarAdministracion);

        Miembro vanesaMarketing = new Miembro("Leo", "Naranja", TipoDocumento.DNI, "0505878");
        Usuario usuariovanesaMarketing= new Usuario(vanesaMarketing.getNombre(),
                vanesaMarketing.getApellido(),
                vanesaMarketing.getTipoDocumento(),
                oscarAdministracion.getNumDoc(),
                "LeoNaranja",
                "LeoNaranja",
                "LeoNaranja@gmail.com",
                rol_miembro);
        vanesaMarketing.setUsuario(usuariovanesaMarketing);
        marketingCocaCola.agregarMiembro(vanesaMarketing);

        Miembro edgardoVentas = new Miembro("Lucía", "Anana", TipoDocumento.DNI, "065848");
        Usuario usuarioedgardoVentas= new Usuario(edgardoVentas.getNombre(),
                edgardoVentas.getApellido(),
                edgardoVentas.getTipoDocumento(),
                edgardoVentas.getNumDoc(),
                "LucíaAnana",
                "LucíaAnana",
                "LucíaAnana@gmail.com",
                rol_miembro);
        edgardoVentas.setUsuario(usuarioedgardoVentas);
        ventasCocaCola.agregarMiembro(edgardoVentas);

        Miembro teresaMarketing = new Miembro("Sofía", "Mandarina", TipoDocumento.DNI, "635107");
        Usuario usuarioteresaMarketing= new Usuario(teresaMarketing.getNombre(),
                teresaMarketing.getApellido(),
                teresaMarketing.getTipoDocumento(),
                teresaMarketing.getNumDoc(),
                "SofíaMandarina",
                "SofíaMandarina",
                "SofíaMandarina@gmail.com",
                rol_miembro);
        teresaMarketing.setUsuario(usuarioedgardoVentas);
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

        //USUARIO DE LA ORGANIZACION
        Usuario usuarioCocacolaJumbo= new Usuario("Jumbo",
                "Jumbo",
                TipoDocumento.PASAPORTE,
                "123456",
                "Jumbo",
                "Jumbo",
                "Jumbo@gmail.Jumbo.com",
                repositorioRol.buscar(3));
        cocaCola.setUsuario(usuarioCocacolaJumbo);
        return cocaCola;
    }

    public void agregarSectorTerritorialYAgenteSectorial(){
        SectorTerritorial sectorBonaerense = new SectorTerritorial();

        Localidad ezeiza = repositorioLocalidad.buscarPorId(Long.parseLong("6270010001"));
        Provincia buenosAires = repositorioProvincia.buscarPorId(6);
        sectorBonaerense.agregarTerritorio(ezeiza);
        sectorBonaerense.agregarTerritorio(buenosAires);

        Usuario usuarioAgenteSectorial= new Usuario("Bonaerense",
                "Bonaerense",
                DNI,
                "2283383",
                "Bonaerense",
                "Bonaerense",
                "Bonaerense@gmail.com",
                repositorioRol.buscar(4));

        AgenteSectorial agenteSectorialBonaerense = new AgenteSectorial(usuarioAgenteSectorial.getNombre(),
                usuarioAgenteSectorial.getApellido(),
                sectorBonaerense,
                usuarioAgenteSectorial);

        repositorioAgenteSectorial.guardar(agenteSectorialBonaerense);

    }

    public void crearAdministrador(){
        Administrador administrador = new Administrador();
        administrador.setNombreDeUsuario("Administrador");
        administrador.setContrasenia("Administrador");
        administrador.setNombre("Administrador");
        administrador.setApellido("Administrador");
        administrador.setTipoDocumento(DNI);
        administrador.setNumeroDocumento("6838743");
        administrador.setEmail("Administrador@admi.com");
        administrador.setRol(repositorioRol.buscar(1));

        repositorioUsuario.guardar(administrador);
    }

}
