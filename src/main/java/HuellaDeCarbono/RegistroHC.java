package HuellaDeCarbono;

import EntidadPersistente.EntidadPersistente;
import Organizacion.Organizacion;
import db.EntityManagerHelper;
import lombok.Getter;
import lombok.Setter;
import unidad.KG;
import unidad.Unidad;

import javax.persistence.*;
import java.time.LocalDate;

import static HuellaDeCarbono.TipoRegistro.TOTAL;

@Getter
@Setter
@Entity
@Table(name = "registro")
public class RegistroHC extends EntidadPersistente {
    @Column(name = "fecha_de_registro",columnDefinition = "DATE")
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private TipoRegistro tipoRegistro;

    @OneToOne(cascade = CascadeType.ALL)
    private HuellaDeCarbono valorHCDatoActividad;

    @OneToOne(cascade = CascadeType.ALL)
    private HuellaDeCarbono valorHCTrayecto;

    @OneToOne(cascade = CascadeType.ALL)
    private HuellaDeCarbono valorHCTotal;

    public RegistroHC(){};

    public RegistroHC(HuellaDeCarbono valorHCDatoActividad, HuellaDeCarbono valorHCTrayecto,HuellaDeCarbono valorHCTotal,TipoRegistro tipoRegistro){
        this.fecha = LocalDate.now();
        this.tipoRegistro = tipoRegistro;
        this.valorHCDatoActividad= valorHCDatoActividad;
        this.valorHCTrayecto= valorHCTrayecto;
        this.valorHCTotal = valorHCTotal;
     }

     public void imprimir(Organizacion organizacion){
        System.out.println("REGISTRO DE HUELLA DE CARBONO CORRESPONDIENTE A LA ORGANIZACION: " + organizacion.getRazonSocial());
        System.out.println("FECHA: " + fecha + "|| DE TIPO: " + tipoRegistro.toString());
        System.out.println("Huella de carbono de los datos de actividad:");
        System.out.println(valorHCDatoActividad.getValor() + " " + valorHCDatoActividad.getTipoDeUnidad().getUnidad());
        System.out.println("Huella de carbono de los trayectos:");
        System.out.println(valorHCTrayecto.getValor() + " " + valorHCTrayecto.getTipoDeUnidad().getUnidad());
        System.out.println("Huella de carbono Total:");
        System.out.println(valorHCTotal.getValor() + " " + valorHCTotal.getTipoDeUnidad().getUnidad());
        System.out.println();
     }
    public void pasarDatosDeActividadAGR(){
        valorHCTotal.getTipoDeUnidad().pasarAGR(valorHCTotal);
    }

}
