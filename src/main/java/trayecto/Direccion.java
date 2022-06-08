package trayecto;

public class Direccion {
    private Calle calle;
    private int altura;
    private Localidad localidad;
    private Provincia provincia;

    public Direccion(Calle calle, int altura, Localidad localidad, Provincia provincia) {
        this.calle = calle;
        this.altura = altura;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Calle getCalle() {
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
