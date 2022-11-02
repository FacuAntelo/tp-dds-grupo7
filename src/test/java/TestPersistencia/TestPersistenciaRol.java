package TestPersistencia;


import models.Usuarios.Permiso;
import models.Usuarios.Rol;
import models.db.EntityManagerHelper;
import org.junit.Test;

public class TestPersistenciaRol {

    @Test
    public void persistirRolAdmin(){
        Rol admin = new Rol();
        admin.setNombre("Admin");

        admin.agregarPermiso(Permiso.CREAR_ORGANIZACIONES);
        admin.agregarPermiso(Permiso.VER_ORGANIZACIONES);
        admin.agregarPermiso(Permiso.EDITAR_ORGANIZACIONES);
        admin.agregarPermiso(Permiso.ELIMIAR_ORGANIZACIONES);

        admin.agregarPermiso(Permiso.CREAR_SECTORES);
        admin.agregarPermiso(Permiso.VER_SECTORES);
        admin.agregarPermiso(Permiso.EDITAR_SECTORES);
        admin.agregarPermiso(Permiso.ELIMINAR_SECTORES);

        admin.agregarPermiso(Permiso.VER_PETICIONES);
        admin.agregarPermiso(Permiso.ACEPTAR_PETICIONES);
        admin.agregarPermiso(Permiso.RECHAZAR_PETICIONES);

        admin.agregarPermiso(Permiso.REGISTRAR_MEDICIONES);
        admin.agregarPermiso(Permiso.VER_REPORTES);

        admin.agregarPermiso(Permiso.VER_RECOMENDACIONES);

        admin.agregarPermiso(Permiso.CREAR_TRAYECTOS);
        admin.agregarPermiso(Permiso.VER_TRAYECTOS);
        admin.agregarPermiso(Permiso.EDITAR_TRAYECTOS);
        admin.agregarPermiso(Permiso.ELIMINAR_TRAYECTOS);

        admin.agregarPermiso(Permiso.CALCULAR_HUEYA_CARBONO);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(admin);
        EntityManagerHelper.commit();
    }


    @Test
    public void persistirRolMiembro(){
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

    @Test
    public void persistirRolOrganizacion(){
        Rol organizacion = new Rol();
        organizacion.setNombre("Organizacion");

        organizacion.agregarPermiso(Permiso.CALCULAR_HUEYA_CARBONO);
        organizacion.agregarPermiso(Permiso.VER_ORGANIZACIONES);
        organizacion.agregarPermiso(Permiso.CREAR_ORGANIZACIONES);
        organizacion.agregarPermiso(Permiso.EDITAR_ORGANIZACIONES);

        organizacion.agregarPermiso(Permiso.VER_SECTORES);
        organizacion.agregarPermiso(Permiso.CREAR_SECTORES);
        organizacion.agregarPermiso(Permiso.EDITAR_SECTORES);
        organizacion.agregarPermiso(Permiso.ELIMINAR_SECTORES);

        organizacion.agregarPermiso(Permiso.REGISTRAR_MEDICIONES);
        organizacion.agregarPermiso(Permiso.CALCULAR_HUEYA_CARBONO);
        organizacion.agregarPermiso(Permiso.VER_REPORTES);
        organizacion.agregarPermiso(Permiso.VER_RECOMENDACIONES);

        organizacion.agregarPermiso(Permiso.VER_PETICIONES);
        organizacion.agregarPermiso(Permiso.ACEPTAR_PETICIONES);
        organizacion.agregarPermiso(Permiso.RECHAZAR_PETICIONES);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(organizacion);
        EntityManagerHelper.commit();
    }

    @Test
    public void persistirRolAgenteSectorial(){
        Rol agenteSectorial = new Rol();
        agenteSectorial.setNombre("AgenteSectorial");

        agenteSectorial.agregarPermiso(Permiso.VER_RECOMENDACIONES);
        agenteSectorial.agregarPermiso(Permiso.VER_REPORTES);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(agenteSectorial);
        EntityManagerHelper.commit();
    }
}
