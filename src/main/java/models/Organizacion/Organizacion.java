package models.Organizacion;
import models.AgenteSectorial.SectorTerritorial;
import models.CargaExcel.ExcelUtils;
import models.EntidadPersistente.EntidadPersistente;
import models.HuellaDeCarbono.CalculadoraHC;
import models.HuellaDeCarbono.HuellaDeCarbono;
import models.HuellaDeCarbono.RegistroHC;
import models.HuellaDeCarbono.TipoRegistro;
import models.Miembro.Miembro;
import models.Miembro.TipoDocumento;
import models.Notificacion.Notificacion;
import lombok.Getter;
import lombok.Setter;
import models.Sector.Sector;
import models.Usuarios.FactorDeEmision;
import models.Usuarios.Usuario;
import models.ValidacionExterna.ValidadorExterno;
import models.trayecto.Localidad;
import models.trayecto.Provincia;
import models.trayecto.Tramo;
import models.trayecto.Trayecto;
import repositories.RepositorioFactoresDeEmision;
import repositories.RepositorioOrganizacion;

import javax.persistence.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "organizacion")
public class Organizacion extends EntidadPersistente {

    @Column(name = "razon_social")
    private String razonSocial;

     @Enumerated(EnumType.STRING)
     @Column(name = "tipo_organizacion")
    private TipoOrganizacion tipoOrganizacion;

     @OneToOne(cascade = CascadeType.ALL)
     @JoinColumn(name= "ubicacion_id",referencedColumnName = "id")
     private Ubicacion ubicacion;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private List<Sector> sectores;

    @Embedded
    private Clasificacion clasificacion;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_organizacion",referencedColumnName = "id")
    private List<RegistroHC> registrosHC = new ArrayList<>();

    @Transient
    private List<RegistroHC> registroHCTotales = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name= "id_organizacion",referencedColumnName = "id")
    private List<Contacto> contactos;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_localidad")
    private Localidad localidad;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_provincia")
    private Provincia provincia;

    @Transient
    private List<Trayecto> trayectosDeLosMiembros;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name= "id_organizacion",referencedColumnName = "id")
    private List<DatosDeActividad> datosDeActividad;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "sector_territorial_organizacion",
            joinColumns = @JoinColumn(name = "organizacion_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_territorial_id",referencedColumnName = "id")
    )
    private Set<SectorTerritorial> sectoresTerritoriales;

    @Transient
    private ExcelUtils lectorExcel; // TODO NO DEBERIA SER PARTE DE LA ENTIDAD UN LECTOR DE EXCEL, QUE TIENE QUE VER EL EXCEL CON LA ORGANIZACION

    @OneToMany(fetch =FetchType.LAZY, mappedBy = "organizacion",cascade = CascadeType.REFRESH )
    private List<Peticion> peticiones;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Organizacion() {
        this.datosDeActividad = new ArrayList<>();
        this.sectores = new ArrayList<>();
        this.contactos= new ArrayList<>();
        this.sectoresTerritoriales = new HashSet<>();
        this.trayectosDeLosMiembros = new ArrayList<>();
    }

    public Organizacion(String razonSocial,TipoOrganizacion tipoOrganizacion, Clasificacion clasificacion, Ubicacion ubicacion){
        this.razonSocial = razonSocial;
        this.tipoOrganizacion = tipoOrganizacion;
        this.clasificacion=clasificacion;
        this.ubicacion=ubicacion;
        this.lectorExcel = new ExcelUtils();
        this.datosDeActividad = new ArrayList<>();
        this.sectores = new ArrayList<>();
        this.contactos= new ArrayList<>();
        this.sectoresTerritoriales = new HashSet<>();
        this.trayectosDeLosMiembros = new ArrayList<>();
        this.provincia = ubicacion.getDireccion().getProvincia();
        this.localidad= ubicacion.getDireccion().getLocalidad();
        this.sectoresTerritoriales.add(ubicacion.getDireccion().getLocalidad().getSector());
        this.sectoresTerritoriales.add(ubicacion.getDireccion().getProvincia().getSector());
    }

    public void agregarMiembroASector(Sector sector, Miembro miembro){
        sector.agregarMiembro(miembro);
    }

    public RegistroHC devolverUltimoRegistro(){
        List<RegistroHC> resultado = this.registrosHC.stream().filter(r -> r.getTipoRegistro() == TipoRegistro.TOTAL).collect(Collectors.toList());
        resultado.sort(Comparator.comparing(a -> a.getFecha()));
        RegistroHC registroHC = new RegistroHC();
        HuellaDeCarbono huellaDeCarbono = new HuellaDeCarbono();
        huellaDeCarbono.setValor(0);
        registroHC.setValorHCTotal(huellaDeCarbono);
        return resultado.size() == 0 ?registroHC: resultado.get(0);
    }


    public RegistroHC calcularHC(){
        RegistroHC registro = CalculadoraHC.calcularHC(this);
        registrosHC.add(registro);
        RegistroHC registroTotal;
        if(registroHCTotales.isEmpty()){
            HuellaDeCarbono huellaHCTotalTramos = new HuellaDeCarbono(registro.getValorHCTrayecto().getValor());
            HuellaDeCarbono huellaHCTotalDA = new HuellaDeCarbono(registro.getValorHCDatoActividad().getValor());
            HuellaDeCarbono huellaHCTotal = new HuellaDeCarbono(registro.getValorHCTotal().getValor());
            registroTotal = new RegistroHC(registro.getValorHCDatoActividad(),registro.getValorHCTrayecto(),registro.getValorHCTotal(), TipoRegistro.TOTAL);
        } else {
            RegistroHC registroTotalAnterior = registroHCTotales.get(registroHCTotales.size()-1);
            HuellaDeCarbono huellaHCTotalTramos = new HuellaDeCarbono(registroTotalAnterior.getValorHCTrayecto().getValor()+registro.getValorHCTrayecto().getValor());
            HuellaDeCarbono huellaHCTotalDA = new HuellaDeCarbono(registroTotalAnterior.getValorHCDatoActividad().getValor()+registro.getValorHCDatoActividad().getValor());
            HuellaDeCarbono huellaHCTotal = new HuellaDeCarbono(registroTotalAnterior.getValorHCTotal().getValor()+registro.getValorHCTotal().getValor());

            registroTotal = new RegistroHC(huellaHCTotalDA,huellaHCTotalTramos,huellaHCTotal,TipoRegistro.TOTAL);
        }
        registroHCTotales.add(registroTotal);
        registrosHC.add(registroTotal);
        registro.imprimir(this);
        registroTotal.imprimir(this);
        return registro;
    }

    public RegistroHC getUltimoRegistroHCTotal(){
        return registroHCTotales.get(registroHCTotales.size()-1);
    }




    public void leerExcel(String path) throws IOException {
        RepositorioFactoresDeEmision repositorioFactoresDeEmision = new RepositorioFactoresDeEmision();

        this.datosDeActividad = lectorExcel.leerExcel(path);

        this.datosDeActividad.forEach( d ->{
            FactorDeEmision fe = repositorioFactoresDeEmision.buscarPorNombre(d.getTipoDeConsumo().toUpperCase());
            d.setFactorDeEmision(fe);
        });
    }

    public void recibePeticion(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento, ValidadorExterno validadorPertenencia){
        if(puedeSerMiembro(nombre, apellido, tipoDocumento, nroDocumento, validadorPertenencia)){
            darDeAltaMiembro(nombre, apellido,tipoDocumento, nroDocumento,validadorPertenencia);
        }else{
            System.out.print("No se puede dar de alta a la parsona");
        }
    }


    public void darDeAltaMiembro(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento, ValidadorExterno validadorExterno){
        validadorExterno.sectorAlQuePertenece(nroDocumento).agregarMiembro(new Miembro(nombre, apellido, tipoDocumento, nroDocumento));
    }

    public boolean puedeSerMiembro(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento, ValidadorExterno validadorPertenencia){
        return validadorPertenencia.perteneceMiembro(nombre, apellido, tipoDocumento, nroDocumento);
    }


    public void agregarSector(Sector sector){
        this.sectores.add(sector);
    }

    public Integer getPosSectorActual(Sector sector){
        return this.sectores.indexOf(sector);
    }


    public List<Tramo> obtenerTramosDeLosMiembros(){
        List<Tramo> tramos = trayectosDeLosMiembros.stream().flatMap(t -> t.getTramos().stream()).collect(Collectors.toList());
        return tramos;
    }


    public void cargarDA(String path) throws IOException {
        this.datosDeActividad = lectorExcel.leerExcel(path);
    }

    //ENTREGA 3 --> PUNTO 4: CONTACTOS
    public void agregarContacto(Contacto uncontacto){contactos.add(uncontacto);};

    //ENTREGA 3 --> PUNTO 5: NOTIFICACIONES
    public void activarNotificaciones(Notificacion notificacion){
        if (tieneContactos()){
            notificacion.agregarOrganizacionANotificar(this);
        }
        else System.out.println("No tiene contactos para ser notificado");
    }

    public boolean tieneContactos(){return !contactos.isEmpty();}


    public void serNotificado(String linkGuiaDeRecomendaciones, Date fecha) {
        if (tieneContactos()){
            contactos.forEach(contacto -> contacto.serNotificado(linkGuiaDeRecomendaciones,fecha));
        }
        else System.out.println("No tiene contactos para ser notificado");
    }

    public void agregarTrayecto(Trayecto trayecto){
        this.trayectosDeLosMiembros.add(trayecto);
    }

    public List<Miembro> obtenerMiembros(){
        List<Miembro> resultado = null;
        sectores.forEach(sector -> resultado.addAll(sector.getMiembros()));
        return resultado;
    }

    public void agregarPeticion(Peticion peticion){
        peticiones.add(peticion);
    }


    public void rechazarPeticionesPendientesDelUsuario(int idUsuario){
       peticiones.forEach(peticion -> {
           if( peticion.getEstadoPeticion()==EstadoPeticion.PENDIENTE && peticion.getUsuario().getId()==idUsuario){
               peticion.setEstadoPeticion(EstadoPeticion.RECHAZADA);
           }
       });
    }
}
