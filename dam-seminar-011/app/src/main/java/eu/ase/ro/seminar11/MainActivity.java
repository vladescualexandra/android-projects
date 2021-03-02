package eu.ase.ro.seminar11;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import eu.ase.ro.seminar11.firebase.Callback;
import eu.ase.ro.seminar11.firebase.FirebaseService;
import eu.ase.ro.seminar11.util.Coach;
import eu.ase.ro.seminar11.util.CoachAdapter;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText tietName;
    private TextInputEditText tietTeam;
    private Spinner spnRole;
    private Button btnClearFields;
    private FloatingActionButton fabDelete;
    private FloatingActionButton fabSave;
    private ListView lvCoaches;

    private List<Coach> coaches = new ArrayList<>();

    private int selectedCoachIndex = -1;
    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        firebaseService = FirebaseService.getInstance();
        firebaseService.attachDataChangeEventListener(dataChangeCallback());
    }

    private Callback<List<Coach>> dataChangeCallback() {
        return new Callback<List<Coach>>() {
            @Override
            public void runResultOnUiThread(List<Coach> result) {
                //primim raspunsul de la attachDataChangeEventListener
                //declansat de fiecare data cand se produc modificari asupra bazei de date
                if (result != null) {
                    coaches.clear();
                    coaches.addAll(result);
                    notifyLvAdapter();
                    clearFields();
                }
            }
        };
    }

    private void initComponents() {
        tietName = findViewById(R.id.tiet_main_name);
        tietTeam = findViewById(R.id.tiet_main_team);
        spnRole = findViewById(R.id.spn_main_roles);
        btnClearFields = findViewById(R.id.btn_main_clear_fields);
        fabDelete = findViewById(R.id.fab_main_delete);
        fabSave = findViewById(R.id.fab_main_save);
        lvCoaches = findViewById(R.id.lv_main_coaches);
        setSpnAdapter();
        setLvAdapter();
        btnClearFields.setOnClickListener(clearFieldsEventListener());
        fabDelete.setOnClickListener(deleteEventListener());
        fabSave.setOnClickListener(saveEventListener());
        lvCoaches.setOnItemClickListener(coachSelectEventListener());
    }

    private AdapterView.OnItemClickListener coachSelectEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCoachIndex = position;
                tietName.setText(coaches.get(selectedCoachIndex).getName());
                tietTeam.setText(coaches.get(selectedCoachIndex).getTeam());
                selectRole(coaches.get(selectedCoachIndex).getRole());
            }
        };
    }

    private View.OnClickListener saveEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Coach coach = createCoachFromView();
                    firebaseService.upsert(coach);
                }
            }
        };
    }

    private View.OnClickListener deleteEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCoachIndex != -1) {
                    firebaseService.delete(coaches.get(selectedCoachIndex));
                }
            }
        };
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

    private Coach createCoachFromView() {
        //selectedCoachIndex este o variabila care retine pozitia din ListView pe care a data click
        // utilizatorul sau -1 in caz contrar
        //este important sa avem acest id pentru a putea executa corect logica din FirebaseService.upsert
        String id = selectedCoachIndex >= 0 ? coaches.get(selectedCoachIndex).getId() : null;
        String name = tietName.getText().toString();
        String team = tietTeam.getText().toString();
        String role = spnRole.getSelectedItem().toString();
        return new Coach(id, name, team, role);
    }

    private void setSpnAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.coach_role_values, android.R.layout.simple_spinner_dropdown_item);
        spnRole.setAdapter(adapter);
    }

    private void setLvAdapter() {
        CoachAdapter adapter = new CoachAdapter(getApplicationContext(), R.layout.lv_row_view,
                coaches, getLayoutInflater());
        lvCoaches.setAdapter(adapter);
    }

    private void notifyLvAdapter() {
        CoachAdapter coachAdapter = (CoachAdapter) lvCoaches.getAdapter();
        coachAdapter.notifyDataSetChanged();
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

    private void clearFields() {
        tietName.setText(null);
        tietTeam.setText(null);
        spnRole.setSelection(0);
        selectedCoachIndex = -1;
    }
}