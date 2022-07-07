package AgenteSectorial;

import trayecto.Localidad;
import trayecto.Provincia;

public class AgenteSectorial {

    private String nombre;
    private String apellido;
    private Provincia provincia;
    private Localidad localidad;

    public AgenteSectorial(String nombre, String apellido, Provincia provincia, Localidad localidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.provincia = provincia;
        this.localidad = localidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public Localidad getLocalidad() {
        return localidad;
    }
}
