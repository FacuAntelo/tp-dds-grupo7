package repositories;


import models.Usuarios.Usuario;
import models.db.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;


public class RepositorioUsuario {
    public Usuario buscarPorNombreUsuarioYContrasenia(String nombreUsuario, String constrasenia){
        return EntityManagerHelper.getEntityManager().
                createQuery("from Usuario as u where u.nombreDeUsuario= :usuario and u.contrasenia= :pass", Usuario.class)
                .setParameter("usuario",nombreUsuario)
                .setParameter("pass",constrasenia)
                .getSingleResult();

    }
    public Usuario find(Integer id){
        return EntityManagerHelper.getEntityManager().find(Usuario.class,id);
    }

    public boolean existeUsuarioConNombreUsuario(String nombreUsuario){
        EntityManager em = EntityManagerHelper.getEntityManager();
        Usuario usuario;
        try {
            usuario = em.createQuery("select u from Usuario u where u.nombreDeUsuario = :nombreUsuario", Usuario.class).setParameter("nombreUsuario", nombreUsuario)
                    .getSingleResult();
        } catch(NoResultException resultException){
            return false;
        }
        return true;
    }

    public void guardar(Usuario usuario) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        EntityManagerHelper.beginTransaction();
        em.persist(usuario);
        EntityManagerHelper.commit();
    }
}
