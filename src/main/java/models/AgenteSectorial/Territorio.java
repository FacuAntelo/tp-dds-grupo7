package models.AgenteSectorial;

import models.HuellaDeCarbono.RegistroHC;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Territorio {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sector_territorial",referencedColumnName = "id")
    private SectorTerritorial sector;

    public abstract RegistroHC calcularHC();
}
