package com.example.dam_exam_subject_002;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.dam_exam_subject_002.util.MType;
import com.example.dam_exam_subject_002.util.Marriage;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.text.DateFormat;
import java.time.Clock;
import java.util.Calendar;

public class MarriageActivity extends AppCompatActivity {

    public static final String MARRIAGE_KEY = "marriage_key";
    TextInputEditText tiet_name;
    Button btn_date;
    Button btn_time;
    Spinner spn_hall;
    SeekBar sb_guests;
    RadioGroup rg_type;
    Button btn_save;

    Intent intent;
    Marriage marriage;

    final Calendar c = Calendar.getInstance();
    int c_hour = c.get(Calendar.HOUR_OF_DAY);
    int c_minute = c.get(Calendar.MINUTE);
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marriage);

        intent = getIntent();
        marriage = new Marriage();
        initComponents();
    }

    private void initComponents() {
        tiet_name = findViewById(R.id.marriage_name);
        btn_date = findViewById(R.id.marriage_date);
        btn_time = findViewById(R.id.marriage_time);
        spn_hall = findViewById(R.id.marriage_hall);
        sb_guests = findViewById(R.id.marriage_guests);
        rg_type = findViewById(R.id.marriage_type);
        btn_save = findViewById(R.id.marriage_save);
        btn_save.setOnClickListener(saveEvent());

        setSpinnerAdapter();
        setDate();
        setTime();
    }

    private View.OnClickListener saveEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tiet_name.getText().toString().trim().length() < 3) {
                    tiet_name.setError("Name too short.");
                } else {
                    tiet_name.setError(null);

                    marriage.setName(tiet_name.getText().toString().trim()); // EditText
                    marriage.setGuests(sb_guests.getProgress()); // SeekBar
                    marriage.setHall(spn_hall.getSelectedItem().toString()); // Spinner

                    // RadioGroup, RadioButton
                    MType type = MType.MM;
                    if (rg_type.getCheckedRadioButtonId() == R.id.marriage_type_ff) {
                        type = MType.FF;
                    } else if (rg_type.getCheckedRadioButtonId() == R.id.marriage_type_fm) {
                        type = MType.FM;
                    }
                    marriage.setType(type);

                    intent.putExtra(MARRIAGE_KEY, (Serializable) marriage);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private void setTime() {
        marriage.setTime(Marriage.fromHM(c_hour, c_minute));
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TimePickerDialog timePickerDialog = new TimePickerDialog(MarriageActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                marriage.setTime(Marriage.fromHM(hourOfDay, minute));
                            }
                        }, c_hour, c_minute, true);
                timePickerDialog.show();
            }
        });
    }

    private void setDate() {
        marriage.setDate(Marriage.fromDMY(day, month, year));
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(MarriageActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        marriage.setDate(Marriage.fromDMY(dayOfMonth, month, year));
                                    }
                                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void setSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Marriage.HALLS);
        spn_hall.setAdapter(adapter);
    }
}