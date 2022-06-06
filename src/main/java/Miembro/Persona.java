package Miembro;

import Organizacion.Organizacion;

public class Persona {
    protected String nombre;
    protected String apellido;
    protected Integer numeroDoc;
    public TipoDePerfil tipoPerfil;

    public Persona(){
        this.tipoPerfil = new Fake();
    }

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
