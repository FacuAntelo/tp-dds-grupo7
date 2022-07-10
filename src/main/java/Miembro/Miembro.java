package Miembro;
import trayecto.*;
import Organizacion.Organizacion;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Miembro{
    private String nombre;
    private String apellido;
    private TipoDocumento tipoDocumento;
    private String numDoc;
    private List<Trayecto> trayectos;



    public Miembro(String nombre, String apellido, TipoDocumento tipoDocumento, String numDoc) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.numDoc = numDoc;
        this.trayectos = new ArrayList<>();
    }

    public String getNombre() {return nombre;}
    public String getApellido() {return apellido;}
    public TipoDocumento getTipoDocumento() {return tipoDocumento;}
    public String getNumDoc() {return numDoc;}
    public List<Trayecto> getTrayectos(){
        return trayectos;
    }



    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setApellido(String apellido) {this.apellido = apellido;}
    public void setTipoDocumento(TipoDocumento tipoDocumento) {this.tipoDocumento = tipoDocumento;}
    public void setNumDoc(String numDoc) {this.numDoc = numDoc;}
    public void agregarTrayecto(Trayecto trayecto){
        trayectos.add(trayecto);
    }



    public Double calcularHC(){
        return this.trayectos.stream().mapToDouble(trayecto -> trayecto.getHCTotalTramos()).sum();
        //Traer datos de da del exel + lista de fe del usuario administrador(Falta crear) que incorpora los datos de un txt
        //Tener en cuenta que el calculo HC Por miembro es de la suma de sus trayectos. A eso debemos sumar las actividades del exel
        //La huella de carbono de cada tramo hay que agregarsela al miembro propietario del tramo
        //Tener en cuenta cuando un miembro pertence a varias organizaciones, el calculo de hc por organizacion puede fallar
        //Como saber si pertenece el tramo a la organizacion? Facil direccionFinalTramo == direccionOrganizacionCalle
    }

}
