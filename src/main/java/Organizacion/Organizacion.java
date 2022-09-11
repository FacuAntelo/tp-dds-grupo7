package Organizacion;
import CargaExcel.ExcelUtils;
import EntidadPersistente.EntidadPersistente;
import HuellaDeCarbono.RegistroHC;
import Notificacion.Notificacion;
import Sector.*;
import Miembro.*;
import ValidacionExterna.*;
import trayecto.Tramo;
import trayecto.Trayecto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Entity
@Table(name = "organizacion")
public class Organizacion extends EntidadPersistente{
    @Column(name = "razonSocial")
    private String razonSocial;

    private TipoOrganizacion tipoOrganizacion;
    private Ubicacion ubicacion;
    private List<Sector> sectores;
    private Clasificacion clasificacion;
    private RegistroHC registrosHC;
    private List<Contacto> contactos;
    private List<Trayecto> trayectosDeLosMiembros;
    private List<DatosDeActividad> datosDeActividad;
    private ExcelUtils lectorExcel; // TODO NO DEBERIA SER PARTE DE LA ENTIDAD UN LECTOR DE EXCEL, QUE TIENE QUE VER EL EXCEL CON LA ORGANIZACION

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

    public void recibePeticion(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento, ValidadorExterno validadorPertenencia){
        if(puedeSerMiembro(nombre, apellido, tipoDocumento, nroDocumento, validadorPertenencia)){
            darDeAltaMiembro(nombre, apellido,tipoDocumento, nroDocumento,validadorPertenencia);
        }else{
            System.out.print("No se puede dar de alta a la parsona");
        }
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
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
    public void calcularHCdeLosMiembros(){
        // CHEQUEAR LOS TRAYECTOS CUYO PUNTO FIN ES LA DIRECCION DE LA ORGANIZACION
       List <Tramo> tramosDeLosMiembros = obtenerTramosDeLosMiembros();
    }

    public void cargarDA(String path) throws IOException {
        this.datosDeActividad = lectorExcel.leerExcel(path);
    }

    public double calcularHCDA() {
        return datosDeActividad.stream().mapToDouble(dato-> {
            try {
                return dato.getValor()*dato.getFactorDeEmision().getValorFactorEmision();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }).sum();
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

    //GETTERS Y SETTERS
    public void setRazonSocial(String razonSocial) {this.razonSocial = razonSocial;}

    public String getRazonSocial() {return razonSocial;}
    public List<Contacto> getContactos() {return contactos;}




}
