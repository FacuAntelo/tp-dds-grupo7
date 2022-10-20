package repositories;

import models.AgenteSectorial.AgenteSectorial;
import models.db.EntityManagerHelper;

import java.util.Arrays;
import java.util.List;

public class RepositorioAgenteSectorial {
    public void guardar(AgenteSectorial... agenteSectorial){
        EntityManagerHelper.beginTransaction();
        List<AgenteSectorial> agenteSectorialList = Arrays.asList(agenteSectorial);
        agenteSectorialList.forEach(EntityManagerHelper::persist);
        EntityManagerHelper.commit();
    }

    public void eliminar(AgenteSectorial... agenteSectorial){
        EntityManagerHelper.beginTransaction();
        List<AgenteSectorial> agenteSectorialList = Arrays.asList(agenteSectorial);
        agenteSectorialList.forEach(EntityManagerHelper.entityManager()::remove);
        EntityManagerHelper.commit();
    }

    public void actualizar(AgenteSectorial... agenteSectorial){
        EntityManagerHelper.beginTransaction();
        List<AgenteSectorial> agenteSectorialList = Arrays.asList(agenteSectorial);
        agenteSectorialList.forEach(EntityManagerHelper.entityManager()::refresh);
        EntityManagerHelper.commit();
    }

    public AgenteSectorial buscar(int id){
        return EntityManagerHelper.getEntityManager().find(AgenteSectorial.class,id);
    }
}
