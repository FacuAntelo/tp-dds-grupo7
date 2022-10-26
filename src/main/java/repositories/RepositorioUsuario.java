package repositories;


import models.Usuarios.Usuario;
import models.db.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;


public class RepositorioUsuario {
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
