package trayecto;

public class Direccion {
    private String calle;
    private int altura;
    private Localidad localidad;
    private Provincia provincia;

    public Direccion(String calle, int altura, Localidad localidad, Provincia provincia) {
        this.calle = calle;
        this.altura = altura;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public String getCalle() {
        return calle;
    }

    public Integer getAltura() {
        return altura;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public Provincia getProvincia() {
        return provincia;
    }
}
