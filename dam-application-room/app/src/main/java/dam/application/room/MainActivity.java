package dam.application.room;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
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

import dam.application.room.async.Callback;
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
        agentService.getAll(getAllCallback());
    }

    private AdapterView.OnItemClickListener actualizeazaEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AdaugaAgentActivity.class);
                intent.putExtra(AdaugaAgentActivity.EXTRA_AGENT, listaAgenti.get(position));
                startActivityForResult(intent, REQUEST_CODE_UPDATE_AGENT);
            }
        };
    }

    private View.OnKeyListener filtrareEventListener() {
        return (v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER) {

                String criteriu = spn_filtru.getSelectedItem().toString();
                String filtru = et_filtru.getText().toString();

                if (criteriu.equals(Filtru.NUME.toString())) {
                    agentService.filtruNume(getAllCallback(), filtru);
                } else if (criteriu.equals(Filtru.TARIF.toString())) {
                    int f = Integer.parseInt(filtru);
                    agentService.filtruTarif(getAllCallback(), f);
                } else if (criteriu.equals(Filtru.SALARIU.toString())) {
                    int f = Integer.parseInt(filtru);
                    agentService.filtruSalariu(getAllCallback(), f);
                }

                return true;
            }
            return false;
        };
    }

    private void setSpinnerFiltru() {
        String[] filtre = {Filtru.NUME.toString(), Filtru.TARIF.toString(), Filtru.SALARIU.toString()};
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        filtre);
        spn_filtru.setAdapter(adapter);
    }

    private View.OnClickListener stergeFiltreEventListener() {
        return v -> {
            agentService.getAll(getAllCallback());
        };
    }

    private View.OnClickListener stergeInregistrariEventListener() {
        return v -> {
            for (AgentVanzare agent : listaAgenti) {
                agentService.delete(stergeInregistrareCallback(agent), agent);
            }
        };
    }

    private Callback<Integer> stergeInregistrareCallback(AgentVanzare agent) {
        return result -> {
            if (result != -1) {
                listaAgenti.remove(agent);
            }
            notifyAdapter();
        };
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
                agentService.insert(insertCallback(), agent);
            } else if (requestCode == REQUEST_CODE_UPDATE_AGENT) {
                agentService.update(updateCallback(), agent);
            }
        }
    }


    private Callback<List<AgentVanzare>> getAllCallback() {
        return result -> {
            if (result != null) {
                listaAgenti.clear();
                listaAgenti.addAll(result);
                notifyAdapter();
            }
        };
    }

    private void getAllV2() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                List<AgentVanzare> results = agentService.getAll();
            }
        }
    }

    private Callback<AgentVanzare> insertCallback() {
        return result -> {
            if (result != null) {
                listaAgenti.add(result);
                notifyAdapter();
            }
        };
    }

    private Callback<AgentVanzare> updateCallback() {
        return result -> {
            if (result != null) {
                for (AgentVanzare agentVanzare: listaAgenti) {
                    Log.e("for", agentVanzare.toString());

                    if (agentVanzare.getId() == result.getId()) {
                        Log.e("runRes", agentVanzare.toString());
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
                notifyAdapter();

            }
        };
    }

}