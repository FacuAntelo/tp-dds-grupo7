package AgenteSectorial;

import EntidadPersistente.EntidadPersistente;
import HuellaDeCarbono.RegistroHC;
import Organizacion.Organizacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sector_territorial")
public class SectorTerritorial extends EntidadPersistente {
    @OneToMany(mappedBy = "sector",cascade = CascadeType.ALL)
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
