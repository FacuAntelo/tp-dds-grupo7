package Usuarios;

import unidad.KG;
import unidad.Unidad;
import lombok.Getter;
import lombok.Setter;

@Setter
 @Getter
public class FactorDeEmision {
    private double valorFactorEmision;
    private String unidad = "CO2eq/";
    private String medidoEn;
    private String masaUnidad;
    private Unidad tipoUnidad;

     public FactorDeEmision(double valorFactorEmision,String medidoEn){
        this.valorFactorEmision = valorFactorEmision;
        this.medidoEn =  medidoEn;
        this.tipoUnidad = KG.getKG();
    }
    public String getUnidad(){
         return tipoUnidad.getUnidad() + unidad + medidoEn;
    }
     public void pasarAKg(){
         this.tipoUnidad.pasarAKG(this);
     }
     public void pasarAGr(){
         this.tipoUnidad.pasarAGR(this);
     }
     public void pasarATN(){
         this.tipoUnidad.pasarATN(this);
     }
 }

