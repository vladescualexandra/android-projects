package dam.application.room;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dam.application.room.model.AgentVanzare;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADAUGA_AGENT = 201;
    private List<AgentVanzare> listaAgenti = new ArrayList<>();

    private Button btn_adauga_agent;
    private ListView lv_agenti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializareComponente();
    }

    private void initializareComponente() {
        btn_adauga_agent = findViewById(R.id.main_adauga_agent);
        btn_adauga_agent.setOnClickListener(adaugaAgentEventListener());

        lv_agenti = findViewById(R.id.main_lv);
        setAdapter();
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
            if (requestCode == REQUEST_CODE_ADAUGA_AGENT) {
                AgentVanzare agent = (AgentVanzare) data.getSerializableExtra(AdaugaAgentActivity.EXTRA_AGENT);
                listaAgenti.add(agent);
                notifyAdapter();
            }
        }
    }
}