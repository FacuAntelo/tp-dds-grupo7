package models.HuellaDeCarbono;

import models.EntidadPersistente.EntidadPersistente;
import models.Organizacion.Organizacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "registro")
public class RegistroHC extends EntidadPersistente {
    @Column(name = "fecha_de_registro",columnDefinition = "DATE")
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private TipoRegistro tipoRegistro;

    @OneToOne(cascade = CascadeType.ALL,fetch= FetchType.EAGER)
    private HuellaDeCarbono valorHCDatoActividad;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private HuellaDeCarbono valorHCTrayecto;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private HuellaDeCarbono valorHCTotal;


    public RegistroHC(){};

    public RegistroHC(HuellaDeCarbono valorHCDatoActividad, HuellaDeCarbono valorHCTrayecto,HuellaDeCarbono valorHCTotal,TipoRegistro tipoRegistro){
        this.fecha = LocalDate.now();
        this.tipoRegistro = tipoRegistro;
        this.valorHCDatoActividad= valorHCDatoActividad;
        this.valorHCTrayecto= valorHCTrayecto;
        this.valorHCTotal = valorHCTotal;
     }
    public RegistroHC(HuellaDeCarbono valorHCDatoActividad, HuellaDeCarbono valorHCTrayecto, HuellaDeCarbono valorHCTotal, TipoRegistro tipoRegistro, LocalDate fecha){
        this.fecha = fecha;
        this.tipoRegistro = tipoRegistro;
        this.valorHCDatoActividad= valorHCDatoActividad;
        this.valorHCTrayecto= valorHCTrayecto;
        this.valorHCTotal = valorHCTotal;
    }
    public static RegistroHC getRegistro (int valorDA, int valorTrayecto, int valorTotal){
        RegistroHC registro = new RegistroHC();

        HuellaDeCarbono huellaTotal = new HuellaDeCarbono();
        huellaTotal.setValor(valorTotal);
        HuellaDeCarbono huellaDatos = new HuellaDeCarbono();
        huellaDatos.setValor(valorDA);
        HuellaDeCarbono huellaTrayectos = new HuellaDeCarbono();
        huellaTrayectos.setValor(valorTrayecto);

        registro.setValorHCTotal(huellaTotal);
        registro.setValorHCDatoActividad(huellaDatos);
        registro.setValorHCTrayecto(huellaTrayectos);

        return registro;
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

     public static RegistroHC unificarRegistros(List<RegistroHC> registroHCList ){
         int valorTotal = registroHCList.stream().mapToInt(r -> r.getValorHCTotal().getValor()).sum();
         int valorDA = registroHCList.stream().mapToInt(r -> r.getValorHCDatoActividad().getValor()).sum();
         int valorTrayecto = registroHCList.stream().mapToInt(r -> r.getValorHCTrayecto().getValor()).sum();

         return RegistroHC.getRegistro(valorDA,valorTrayecto,valorTotal);
     }

    public void pasarDatosDeActividadAGR(){
        valorHCTotal.getTipoDeUnidad().pasarAGR(valorHCTotal);
    }

}
