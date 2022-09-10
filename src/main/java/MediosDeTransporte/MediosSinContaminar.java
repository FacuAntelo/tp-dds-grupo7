package MediosDeTransporte;

public class MediosSinContaminar extends MediosDeTransporte{
    private String nombre;

    public MediosSinContaminar(){
        this.setEsCompartido(false);
        this.setTipoTransporte(TipoTransporte.MEDIOS_SIN_CONTAMINAR);
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public double getValorFactorDeEmision(){
        return 0;
    }
}
