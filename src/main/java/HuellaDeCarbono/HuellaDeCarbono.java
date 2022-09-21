package HuellaDeCarbono;

import EntidadPersistente.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;
import unidad.KG;
import unidad.Unidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
@Entity
@Table(name = "huella_de_carbono")
public class HuellaDeCarbono extends EntidadPersistente {
    @Column(name = "valor")
    private int valor;

    @Column(name = "unidad")
    private String unidad = "CO2eq";

    @Transient
    private Unidad tipoDeUnidad = KG.getKG();

    public HuellaDeCarbono(){}

    public HuellaDeCarbono(int valor){
        this.valor = valor;
        this.unidad = tipoDeUnidad.getUnidad() + this.unidad;
    }


    public HuellaDeCarbono suma(HuellaDeCarbono huella){
        HuellaDeCarbono suma =new HuellaDeCarbono(this.getValor()+ huella.getValor());
        return suma;
    }
    public String getValorConUnidad(){
        return valor + tipoDeUnidad.getUnidad() + unidad;
    }
}
