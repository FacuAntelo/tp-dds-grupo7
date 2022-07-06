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
        return 0.0;
    }

}
