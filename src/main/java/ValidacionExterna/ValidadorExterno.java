package ValidacionExterna;
import Miembro.*;
import Organizacion.Organizacion;
import Sector.Sector;

public interface ValidadorExterno {
    public abstract boolean perteneceMiembro(Persona persona);
    public abstract Sector sectorAlQuePertenece(Persona persona);

}
