package models.AgenteSectorial;

import lombok.Getter;
import lombok.Setter;
import models.EntidadPersistente.EntidadPersistente;
import models.HuellaDeCarbono.RegistroHC;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sector_territorial")
public class SectorTerritorial extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "sector",cascade= CascadeType.ALL)
    List<Territorio> territorioList = new ArrayList<>();


    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name= "id_sector_territorial",referencedColumnName = "id")
    List<RegistroHC> registros = new ArrayList<>();


//    @ManyToMany
//    private List<Organizacion> organizaciones;

    public SectorTerritorial(){
//        this.organizaciones = new ArrayList<>();
    }

    public void agregarTerritorio(Territorio territorio){
        territorioList.add(territorio);
        territorio.setSector(this);
    }
    public void agregarRegistro(RegistroHC registroHC){
        this.registros.add(registroHC);
    }

}
