package Organizacion;
import CargaExcel.ExcelUtils;
import Notificacion.Notificacion;
import Sector.*;
import Miembro.*;
import ValidacionExterna.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Organizacion {
    private String razonSocial;
    private TipoOrganizacion tipoOrganizacion;
    private Ubicacion ubicacion;
    private List<Sector> sectores;
    private String clasificacion;
    private List<Contacto> contactos;
    private List<DatosDeActividad> datosDeActividad;
    private ExcelUtils lectorExcel;

    public Organizacion(){
        this.lectorExcel = new ExcelUtils();
        this.datosDeActividad = new ArrayList<>();
        this.sectores = new ArrayList<>();
        this.contactos= new ArrayList<>();
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

    public Double calcularHCdeLaOrg(){
        return this.sectores.stream().mapToDouble(sector->sector.getTotalEmisionMiembros()).sum() + this.calcularHCDA();
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

    //GETTERS Y SETTERS
    public void setRazonSocial(String razonSocial) {this.razonSocial = razonSocial;}

    public String getRazonSocial() {return razonSocial;}
    public List<Contacto> getContactos() {return contactos;}




}
