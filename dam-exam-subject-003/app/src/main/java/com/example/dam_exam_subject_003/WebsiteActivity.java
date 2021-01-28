package com.example.dam_exam_subject_003;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.dam_exam_subject_003.util.Website;

import java.util.ArrayList;
import java.util.List;

public class WebsiteActivity extends AppCompatActivity {

    public static final String WEBSITE_KEY = "website_key";
    Spinner spn_name;
    DatePicker datePicker;
    TimePicker timePicker;
    RadioGroup rg_type;
    SeekBar sb_span;
    CheckBox cb_incognito;
    Button btn_save;

    Intent intent;
    Website website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        intent = getIntent();
        initComponents();
    }

    private void initComponents() {
        spn_name = findViewById(R.id.website_name);
        datePicker = findViewById(R.id.website_date);
        timePicker = findViewById(R.id.website_time);
        rg_type = findViewById(R.id.website_type);
        sb_span = findViewById(R.id.website_span);
        cb_incognito = findViewById(R.id.website_incognito);
        btn_save = findViewById(R.id.website_save);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>
                (getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, Website.WEBSITES);
        spn_name.setAdapter(adapter);

        btn_save.setOnClickListener(saveEvent());

    }

    private View.OnClickListener saveEvent() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                String name = spn_name.getSelectedItem().toString();
                String date = Website.fromDMY(
                        datePicker.getDayOfMonth(),
                        datePicker.getMonth(),
                        datePicker.getYear()
                );

                String time = Website.fromHM(
                        timePicker.getHour(),
                        timePicker.getMinute()
                );

                String type = (rg_type.getCheckedRadioButtonId() == R.id.website_type_entertainment)
                        ? Website.TYPE_ENTERTAINMENT : Website.TYPE_WORK;

                int span = sb_span.getProgress();
                boolean incognito = cb_incognito.isChecked();

                website = new Website(name, date, time, type, span, incognito);
                intent.putExtra(WEBSITE_KEY, website);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }
}