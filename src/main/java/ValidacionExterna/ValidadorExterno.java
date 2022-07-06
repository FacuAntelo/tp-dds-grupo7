package ValidacionExterna;
import Miembro.*;
import Organizacion.Organizacion;
import Sector.Sector;

public interface ValidadorExterno {
    public abstract boolean perteneceMiembro(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento);
    public abstract Sector sectorAlQuePertenece(String identificador);
}
