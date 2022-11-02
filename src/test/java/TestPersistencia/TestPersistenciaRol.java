package TestPersistencia;


import models.Usuarios.Permiso;
import models.Usuarios.Rol;
import models.db.EntityManagerHelper;
import org.junit.Test;

public class TestPersistenciaRol {
    
    @Test
    public void persistirRol(){
        Rol miembro = new Rol();
        miembro.setNombre("Miembro");

        miembro.agregarPermiso(Permiso.CALCULAR_HUEYA_CARBONO);
        miembro.agregarPermiso(Permiso.VER_TRAYECTOS);
        miembro.agregarPermiso(Permiso.CREAR_TRAYECTOS);
        miembro.agregarPermiso(Permiso.EDITAR_TRAYECTOS);
        miembro.agregarPermiso(Permiso.ELIMINAR_TRAYECTOS);
        miembro.agregarPermiso(Permiso.VER_ORGANIZACIONES);
        
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(miembro);
        EntityManagerHelper.commit();
    }    
}
