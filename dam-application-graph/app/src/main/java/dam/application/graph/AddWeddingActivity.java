package dam.application.graph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.io.Serializable;

public class AddWeddingActivity extends AppCompatActivity {

    Wedding wedding = new Wedding();

    Button btn_save;
    EditText et_name;
    DatePicker dp_date;
    TimePicker tp_time;
    SeekBar sb_guests;
    Spinner spn_hall;

    String HALL_1 = "hall-1";
    String HALL_2 = "hall-2";
    String[] HALLS = {HALL_1, HALL_2};

    Intent intent;
    public static String EXTRA_WEDDING = "wedding";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wedding);

        initComponents();
    }

    private void initComponents() {
        intent = getIntent();
        btn_save = findViewById(R.id.btn_save);
        et_name = findViewById(R.id.add_name);
        dp_date = findViewById(R.id.add_date);
        tp_time = findViewById(R.id.add_time);
        sb_guests = findViewById(R.id.add_guests);
        spn_hall = findViewById(R.id.add_hall);

        btn_save.setOnClickListener(v -> {
            if (validate()) {
                buildWedding();
                intent.putExtra(EXTRA_WEDDING, (Serializable) wedding);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        setSpinnerAdapter();
    }

    private void setSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                HALLS
        );
        spn_hall.setAdapter(adapter);
    }

    private boolean validate() {
        if (et_name.getText() == null
                || et_name.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private void buildWedding() {
        String name = et_name.getText().toString();
        wedding.setName(name);

        wedding.setDate(Util.fromDatePicker(dp_date));
        wedding.setTime(Util.fromTimePicker(tp_time));

        int guests = sb_guests.getProgress();
        wedding.setGuests(guests);

        String hall = spn_hall.getSelectedItem().toString();
        wedding.setHall(hall);
    }
}