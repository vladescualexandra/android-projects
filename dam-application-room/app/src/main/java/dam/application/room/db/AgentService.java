package dam.application.room.db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import dam.application.room.Filtru;
import dam.application.room.model.AgentVanzare;

public class AgentService {

    private final AgentDao agentDao;

    public AgentService(Context context) {
        this.agentDao = DatabaseManager.getInstance(context).getAgentDao();
    }

    public List<AgentVanzare> getAll() {
        return agentDao.getAll();
    }

    public AgentVanzare insert(final AgentVanzare agent) {
        if (agent == null) {
            return null;
        } else {
            long id = agentDao.insert(agent);
            if (id == -1) {
                return null;
            }
            agent.setId(id);
            return agent;
        }
    }

    public List<AgentVanzare> filtru(String criteriu, String filtru) {
        if (criteriu.equals(Filtru.NUME.toString())) {
            return agentDao.filtruNume(filtru);
        } else if (criteriu.equals(Filtru.TARIF.toString())) {
            int f = Integer.parseInt(filtru);
            return agentDao.filtruTarif(f);
        } else if (criteriu.equals(Filtru.SALARIU.toString())) {
            int f = Integer.parseInt(filtru);
            return agentDao.filtruSalariu(f);
        } else {
            return new ArrayList<>();
        }
    }


    public int delete(AgentVanzare agent) {
        if (agent == null) {
            return -1;
        }
        return agentDao.delete(agent);
    }

    public AgentVanzare update(AgentVanzare agent) {
        if (agent == null) {
            return null;
        }
        int count = agentDao.update(agent);
        if (count < 1) {
            return null;
        }
        return agent;
    }
}
