package com.example.dam_seminar_012;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dam_seminar_012.util.Coach;
import com.example.dam_seminar_012.util.CoachAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText tietName;
    private TextInputEditText tietTeam;
    private Spinner spnRole;
    private Button btnClearFields;
    private FloatingActionButton fabDelete;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabChart;
    private ListView lvCoaches;

    private List<Coach> coaches = new ArrayList<>();
    private int selectedCoachIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        tietName = findViewById(R.id.tiet_main_name);
        tietTeam = findViewById(R.id.tiet_main_team);
        spnRole = findViewById(R.id.spn_main_roles);
        btnClearFields = findViewById(R.id.btn_main_clear_fields);
        fabDelete = findViewById(R.id.fab_main_delete);
        fabSave = findViewById(R.id.fab_main_save);
        fabChart = findViewById(R.id.fab_main_chart);
        lvCoaches = findViewById(R.id.lv_main_coaches);
        setSpinnerAdapter();
        setListViewAdapter();
        btnClearFields.setOnClickListener(clearFieldsEventListener());
        fabDelete.setOnClickListener(deleteEventListener());
        fabSave.setOnClickListener(saveEventListener());
        fabChart.setOnClickListener(chartEventListener());
    }

    /************************* CHART **********************************/
    private View.OnClickListener chartEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                intent.putExtra(ChartActivity.COACHES_KEY, (Serializable) coaches);
                startActivity(intent);
            }
        };
    }

    private void setSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.coach_role_values, android.R.layout.simple_spinner_dropdown_item);
        spnRole.setAdapter(adapter);
    }

    private void setListViewAdapter() {
        CoachAdapter adapter = new CoachAdapter(getApplicationContext(), R.layout.main_row_item,
                coaches, getLayoutInflater());
        lvCoaches.setAdapter(adapter);
    }

    private void notifyAdapter() {
        CoachAdapter coachAdapter = (CoachAdapter) lvCoaches.getAdapter();
        coachAdapter.notifyDataSetChanged();
    }

    private void clearFields() {
        tietName.setText(null);
        tietTeam.setText(null);
        spnRole.setSelection(0);
        selectedCoachIndex = -1;
    }

    private void selectRole(String role) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spnRole.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(role)) {
                spnRole.setSelection(i);
                break;
            }
        }
    }

    private Coach createCoachFromView() {
        String id = selectedCoachIndex >= 0 ? coaches.get(selectedCoachIndex).getId() : null;
        String name = tietName.getText().toString();
        String team = tietTeam.getText().toString();
        String role = spnRole.getSelectedItem().toString();
        return new Coach(id, name, team, role);
    }

    private View.OnClickListener clearFieldsEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        };
    }

    private boolean validate() {
        if (tietName.getText() == null || tietName.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    R.string.main_coach_name_error,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (tietTeam.getText() == null || tietTeam.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    R.string.main_coach_team_error,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private View.OnClickListener saveEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Coach coach = createCoachFromView();
                    coaches.add(coach);
                }
            }
        };
    }

    private View.OnClickListener deleteEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCoachIndex != -1) {
                    coaches.remove(selectedCoachIndex);
                }
            }
        };
    }
}