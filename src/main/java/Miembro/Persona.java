package Miembro;

import Organizacion.Organizacion;

public class Persona {
    private String nombre;
    private String apellido;
    private TipoDocumento tipoDocumento;
    private String numDoc;

    /*
    public Persona()

        /*this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDoc = numeroDoc;

    */

    public void solicitarMembresia(Organizacion organizacion) {
        organizacion.agregarMiembroPendiente(this);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
