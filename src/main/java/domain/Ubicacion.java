package domain;

public class Ubicacion {
    int localidad;
    String calle;
    int altura;

    public Ubicacion crearUbicacion(int localidad, String calle, int altura){
        this.localidad = localidad;
        this. calle = calle;
        this.altura = altura;
        return this;
    }

    public int getLocalidad() {
        return localidad;
    }

    public String getCalle() {
        return calle;
    }

    public int getAltura() {
        return altura;
    }
}
