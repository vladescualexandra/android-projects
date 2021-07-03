package dam.application.room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import dam.application.room.model.AgentVanzare;
import dam.application.room.model.Domeniu;

public class AdaugaAgentActivity extends AppCompatActivity {

    public static String EXTRA_AGENT = "agent";
    private Intent intent;
    private AgentVanzare agent;

    EditText et_nume;
    SeekBar sb_tarif;
    RadioGroup rg_domeniu;
    DatePicker dp_data;
    TimePicker tp_timp;
    Spinner spn_salariu;
    CheckBox cb_particular;

    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_agent);

        initializareComponente();

    }

    private void initializareComponente() {
        intent = getIntent();
        agent = new AgentVanzare();
        et_nume = findViewById(R.id.add_nume);
        sb_tarif = findViewById(R.id.add_tarif);
        rg_domeniu = findViewById(R.id.add_rg_domeniu);
        dp_data = findViewById(R.id.add_data);
        tp_timp = findViewById(R.id.add_timp);
        spn_salariu = findViewById(R.id.add_salariu);
        cb_particular = findViewById(R.id.add_particular);

        setSpinner();

        btn_save = findViewById(R.id.add_save);
        btn_save.setOnClickListener(saveEventListener());
    }

    private void setSpinner() {
        String[] salarii = new String[]{"1000", "2000", "3000"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        salarii);
        spn_salariu.setAdapter(adapter);

    }

    private View.OnClickListener saveEventListener() {
        return v -> {
            if (validare()) {
                String nume = et_nume.getText().toString();
                agent.setNume(nume);

                int tarif = sb_tarif.getProgress();
                agent.setTarif(tarif);

                if (rg_domeniu.getCheckedRadioButtonId() == R.id.add_rb_domeniu_1) {
                    agent.setDomeniu(Domeniu.DOMENIU_1.toString());
                } else {
                    agent.setDomeniu(Domeniu.DOMENIU_2.toString());
                }

                String data = Util.fromDatePicker(dp_data);
                agent.setData(data);

                String timp = Util.fromTimePicker(tp_timp);
                agent.setTimp(timp);

                int salariu = Integer.parseInt(spn_salariu.getSelectedItem().toString());
                agent.setSalariu(salariu);

                boolean particular = cb_particular.isChecked();
                agent.setParticular(particular);

                intent.putExtra(EXTRA_AGENT, agent);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Date invalide.",
                        Toast.LENGTH_LONG).show();
            }
        };
    }

    private boolean validare() {
        if (et_nume.getText().toString() == null
                || et_nume.getText().toString().isEmpty()) {
            return false;
        }

        return true;
    }

}