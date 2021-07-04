package dam.application.room;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dam.application.room.db.AgentService;
import dam.application.room.model.AgentVanzare;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADAUGA_AGENT = 201;
    public static final int REQUEST_CODE_UPDATE_AGENT = 202;
    private List<AgentVanzare> listaAgenti = new ArrayList<>();

    Button btn_adauga_agent;
    Button btn_sterge_filtre;
    Button btn_sterge_inregistrari;

    Spinner spn_filtru;
    EditText et_filtru;

    private ListView lv_agenti;

    AgentService agentService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializareComponente();
    }

    private void initializareComponente() {
        btn_adauga_agent = findViewById(R.id.main_adauga_agent);
        btn_adauga_agent.setOnClickListener(adaugaAgentEventListener());

        btn_sterge_filtre = findViewById(R.id.main_sterge_filtrele);
        btn_sterge_filtre.setOnClickListener(stergeFiltreEventListener());

        btn_sterge_inregistrari = findViewById(R.id.main_sterge_inregistrari);
        btn_sterge_inregistrari.setOnClickListener(stergeInregistrariEventListener());

        spn_filtru = findViewById(R.id.main_spn_filtru);
        setSpinnerFiltru();
        et_filtru = findViewById(R.id.main_et_filtru);
        et_filtru.setOnKeyListener(filtrareEventListener());

        lv_agenti = findViewById(R.id.main_lv);
        setAdapter();
        lv_agenti.setOnItemClickListener(actualizeazaEventListener());

        agentService = new AgentService(getApplicationContext());
        getAll();
    }

    private View.OnClickListener stergeFiltreEventListener() {
        return v -> getAll();
    }

    private View.OnClickListener stergeInregistrariEventListener() {
        return v -> {
            for (AgentVanzare agentVanzare : listaAgenti) {
                delete(agentVanzare);
            }
        };
    }

    private AdapterView.OnItemClickListener actualizeazaEventListener() {
        return (parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), AdaugaAgentActivity.class);
            intent.putExtra(AdaugaAgentActivity.EXTRA_AGENT, listaAgenti.get(position));
            startActivityForResult(intent, REQUEST_CODE_UPDATE_AGENT);
        };
    }

    private View.OnKeyListener filtrareEventListener() {
        return (v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER) {

                filtreaza();
                return true;
            }
            return false;
        };
    }

    private void filtreaza() {
        String criteriu = spn_filtru.getSelectedItem().toString();
        String filtru = et_filtru.getText().toString();
        Thread thread = new Thread() {
            @Override
            public void run() {
                List<AgentVanzare> results = agentService.filtru(criteriu, filtru);
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (results != null) {
                            listaAgenti.clear();
                            listaAgenti.addAll(results);
                            notifyAdapter();
                        }
                    }
                });
            }
        };
        thread.start();
    }

    private void setSpinnerFiltru() {
        String[] filtre = {Filtru.NUME.toString(), Filtru.TARIF.toString(), Filtru.SALARIU.toString()};
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        filtre);
        spn_filtru.setAdapter(adapter);
    }


    private void setAdapter() {
        ArrayAdapter<AgentVanzare> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        listaAgenti);
        lv_agenti.setAdapter(adapter);
    }

    private void notifyAdapter() {
        ArrayAdapter<AgentVanzare> adapter = (ArrayAdapter) lv_agenti.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener adaugaAgentEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), AdaugaAgentActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADAUGA_AGENT);
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            AgentVanzare agent = (AgentVanzare) data.getSerializableExtra(AdaugaAgentActivity.EXTRA_AGENT);
            if (requestCode == REQUEST_CODE_ADAUGA_AGENT) {
                insert(agent);
            } else if (requestCode == REQUEST_CODE_UPDATE_AGENT) {
                update(agent);
            }
        }
    }


    private void getAll() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                List<AgentVanzare> results = agentService.getAll();
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (results != null) {
                            listaAgenti.clear();
                            listaAgenti.addAll(results);
                            notifyAdapter();
                        }
                    }
                });
            }
        };
        thread.start();
    }

    private void insert(AgentVanzare agent) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                AgentVanzare result = agentService.insert(agent);
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null) {
                            listaAgenti.add(result);
                            notifyAdapter();
                        }
                    }
                });
            }
        };
        thread.start();
    }

    private void delete(AgentVanzare agent) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                int result = agentService.delete(agent);
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != -1) {
                            listaAgenti.remove(agent);
                            notifyAdapter();
                        }
                    }
                });
            }
        };
        thread.start();
    }


    private void update(AgentVanzare agent) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                AgentVanzare result = agentService.update(agent);
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null) {
                            for (AgentVanzare agentVanzare : listaAgenti) {

                                if (agentVanzare.getId() == result.getId()) {
                                    agentVanzare.setParticular(result.isParticular());
                                    agentVanzare.setSalariu(result.getSalariu());
                                    agentVanzare.setTimp(result.getTimp());
                                    agentVanzare.setData(result.getData());
                                    agentVanzare.setDomeniu(result.getDomeniu());
                                    agentVanzare.setTarif(result.getTarif());
                                    agentVanzare.setNume(result.getNume());
                                    break;
                                }
                            }
                        }
                    }
                });
            }
        };
        thread.start();
    }


}