package repositories;

import models.Organizacion.DatosDeActividad;
import models.Organizacion.Organizacion;
import models.db.EntityManagerHelper;

import java.util.Arrays;
import java.util.List;

public class RepositorioDA {
    public void guardar(Integer organizacionId, List<DatosDeActividad> dAList){
        EntityManagerHelper.beginTransaction();
        RepositorioOrganizacion repositorioOrganizacion= new RepositorioOrganizacion();
        Organizacion organizacionBuscado = repositorioOrganizacion.buscar(organizacionId);
        organizacionBuscado.setDatosDeActividad(dAList);
        repositorioOrganizacion.guardar(organizacionBuscado);
        EntityManagerHelper.commit();
    }
}
