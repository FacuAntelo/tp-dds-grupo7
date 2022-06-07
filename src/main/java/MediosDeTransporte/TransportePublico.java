package MediosDeTransporte;

public class TransportePublico implements MedioDeTransporte{
    private TipoTransportePublico tipo;
    private Linea linea;

    public TransportePublico(TipoTransportePublico tipo) {
        this.tipo = tipo;
    }

    public TipoTransportePublico getTipo() {return tipo;}

    public void setTipo(TipoTransportePublico tipo) {this.tipo = tipo;}

    public Linea getLinea() {return linea;}

    public void setLinea(Linea linea) {this.linea = linea;}
}