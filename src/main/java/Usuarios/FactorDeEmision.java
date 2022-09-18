package Usuarios;

import EntidadPersistente.EntidadPersistente;
import domain.Configurador;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import unidad.KG;
import unidad.Unidad;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Setter
@Getter
@Entity
@Table(name = "factor_emision")
public class FactorDeEmision extends EntidadPersistente {
    @Column(name = "valor")
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, CascadeType.REMOVE })
    private double valorFactorEmision;
    @Column(name = "unidad")
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, CascadeType.REMOVE })
    private String unidad = "CO2eq/";
    @Transient
    private String medidoEn;
    @Transient
    private String masaUnidad;
    @Transient
    private Unidad tipoUnidad;

     public FactorDeEmision(String nombre, double valorFactorEmision,String medidoEn) {
         Configurador configurador = Configurador.getConfigurador();
         configurador.agregarFactor(nombre, this);
         this.valorFactorEmision = valorFactorEmision;
         this.medidoEn = medidoEn;
         this.tipoUnidad = KG.getKG();
         this.unidad = tipoUnidad.getUnidad() + this.unidad + medidoEn;
    }

    public FactorDeEmision() {
        super();
    }

    public String getUnidad(){
         return tipoUnidad.getUnidad() + unidad + medidoEn;
    }
 }

