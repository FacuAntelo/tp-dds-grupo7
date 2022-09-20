package Organizacion;
import CargaExcel.ExcelUtils;
import EntidadPersistente.EntidadPersistente;
import HuellaDeCarbono.CalculadoraHC;
import HuellaDeCarbono.HuellaDeCarbono;
import HuellaDeCarbono.RegistroHC;
import HuellaDeCarbono.TipoRegistro;
import Notificacion.Notificacion;
import Sector.*;
import Miembro.*;
import ValidacionExterna.*;
import lombok.Getter;
import lombok.Setter;
import trayecto.Tramo;
import trayecto.Trayecto;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Getter
@Setter
@Table(name = "organizacion")
public class Organizacion extends EntidadPersistente{

    @Column(name = "razon_social")
    private String razonSocial;

     @Enumerated(EnumType.STRING)
     @Column(name = "tipo_organizacion")
    private TipoOrganizacion tipoOrganizacion;

     @OneToOne
     @JoinColumn(name= "ubicacion_id",referencedColumnName = "id")
    private Ubicacion ubicacion;

    @OneToMany
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private List<Sector> sectores;

    @Embedded
    private Clasificacion clasificacion;

    @OneToMany
    @JoinColumn(name = "id_organizacion",referencedColumnName = "id")
    private List<RegistroHC> registrosHC = new ArrayList<>();

    @Transient
    private List<RegistroHC> registroHCTotales = new ArrayList<>();

    @OneToMany
    @JoinColumn(name= "id_organizacion",referencedColumnName = "id")
    private List<Contacto> contactos;


    @Transient
    private List<Trayecto> trayectosDeLosMiembros;

    @OneToMany
    @JoinColumn(name= "id_organizacion",referencedColumnName = "id")
    private List<DatosDeActividad> datosDeActividad;

    @Transient
    private ExcelUtils lectorExcel; // TODO NO DEBERIA SER PARTE DE LA ENTIDAD UN LECTOR DE EXCEL, QUE TIENE QUE VER EL EXCEL CON LA ORGANIZACION

    public Organizacion() {}

    public Organizacion(String razonSocial,TipoOrganizacion tipoOrganizacion, Clasificacion clasificacion, Ubicacion ubicacion){
        this.razonSocial = razonSocial;
        this.tipoOrganizacion = tipoOrganizacion;
        this.clasificacion=clasificacion;
        this.ubicacion=ubicacion;
        this.lectorExcel = new ExcelUtils();
        this.datosDeActividad = new ArrayList<>();
        this.sectores = new ArrayList<>();
        this.contactos= new ArrayList<>();
        this.trayectosDeLosMiembros = new ArrayList<>();
    }

    public void calcularHC(){
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
    }

    public RegistroHC getUltimoRegistroHCTotal(){
        return registroHCTotales.get(registroHCTotales.size()-1);
    }




    public void leerExcel(String path) throws IOException {
        this.datosDeActividad = lectorExcel.leerExcel(path);
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

}
