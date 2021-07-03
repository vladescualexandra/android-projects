package dam.application.room.db;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import dam.application.room.async.AsyncTaskRunner;
import dam.application.room.async.Callback;
import dam.application.room.model.AgentVanzare;

public class AgentService {

    private final AgentDao agentDao;
    private final AsyncTaskRunner taskRunner;

    public AgentService(Context context) {
        this.agentDao = DatabaseManager.getInstance(context).getAgentDao();
        this.taskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<AgentVanzare>> callback) {
        List<AgentVanzare> listaAgenti = new ArrayList<>();
        Callable<List<AgentVanzare>> callable = new Callable<List<AgentVanzare>>() {
            @Override
            public List<AgentVanzare> call() throws Exception {
                listaAgenti.addAll(agentDao.getAll());
                return listaAgenti;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<AgentVanzare> callback, final AgentVanzare agent) {
        Callable<AgentVanzare> callable = new Callable<AgentVanzare>() {
            @Override
            public AgentVanzare call() throws Exception {
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
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void filtruNume(Callback<List<AgentVanzare>> callback, String filtru) {
        List<AgentVanzare> listaAgenti = new ArrayList<>();
        Callable<List<AgentVanzare>> callable = new Callable<List<AgentVanzare>>() {
            @Override
            public List<AgentVanzare> call() throws Exception {
                listaAgenti.addAll(agentDao.filtruNume(filtru));
                Log.e("test", agentDao.filtruNume(filtru).toString());
                return listaAgenti;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void filtruTarif(Callback<List<AgentVanzare>> callback, int filtru) {
        List<AgentVanzare> listaAgenti = new ArrayList<>();
        Callable<List<AgentVanzare>> callable = new Callable<List<AgentVanzare>>() {
            @Override
            public List<AgentVanzare> call() throws Exception {
                listaAgenti.addAll(agentDao.filtruTarif(filtru));
                return listaAgenti;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void filtruSalariu(Callback<List<AgentVanzare>> callback, int filtru) {
        List<AgentVanzare> listaAgenti = new ArrayList<>();
        Callable<List<AgentVanzare>> callable = new Callable<List<AgentVanzare>>() {
            @Override
            public List<AgentVanzare> call() throws Exception {
                listaAgenti.addAll(agentDao.filtruSalariu(filtru));
                return listaAgenti;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, AgentVanzare agent) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                if (agent == null) {
                    return -1;
                }
                return agentDao.delete(agent);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<AgentVanzare> callback, AgentVanzare agent) {
        Callable<AgentVanzare> callable = new Callable<AgentVanzare>() {
            @Override
            public AgentVanzare call() throws Exception {
                if (agent == null) {
                    return null;
                }
                int count = agentDao.update(agent);
                if (count < 1) {
                    return null;
                }
                return agent;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
}
