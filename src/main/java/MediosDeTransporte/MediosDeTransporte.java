package MediosDeTransporte;

import Combustible.Combustible;
import EntidadPersistente.EntidadPersistente;
import domain.services.entities.DistanciaAPI;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "medio_de_transporte")
@DiscriminatorColumn(name = "medio")
public abstract class MediosDeTransporte extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "combustible_id",referencedColumnName = "id")
    private Combustible combustible;

    @Column(name = "es_compartido")
    private Boolean esCompartido;

    @Enumerated(EnumType.STRING)
    private TipoTransporte tipoTransporte;

    @Column(name = "consumo_x_km")
    private Double consumoXKM = 10.0;

    public void setTipoTransporte(TipoTransporte tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }

    public TipoTransporte getTipoTransporte() {
        return tipoTransporte;
    }

    public Boolean getEsCompartido() {
        return esCompartido;
    }

    public void setEsCompartido(Boolean esCompartido) {
        this.esCompartido = esCompartido;
    }

    public double getHC(DistanciaAPI distancia) throws IOException {
        return this.combustible.getFactorEmision().getValorFactorEmision()*distancia.getValor();
    }
    public double getValorFactorDeEmision(){
        return this.getCombustible().getFactorEmision().getValorFactorEmision();
    }
}
