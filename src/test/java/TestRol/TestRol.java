package TestRol;

import models.Organizacion.Organizacion;
import models.Usuarios.Permiso;
import models.Usuarios.Rol;
import models.db.EntityManagerHelper;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestRol {
    @Test
    public void testRol(){
        Rol rol = new Rol();
        rol.agregarPermiso(Permiso.ACEPTAR_PETICIONES);
        rol.agregarPermiso(Permiso.CREAR_SECTORES);
        rol.agregarPermiso(Permiso.CREAR_ORGANIZACIONES);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(rol);
        EntityManagerHelper.commit();

    }
}
