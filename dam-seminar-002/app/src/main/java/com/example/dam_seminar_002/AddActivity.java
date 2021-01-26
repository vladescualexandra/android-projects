package com.example.dam_seminar_002;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Array;

public class AddActivity extends AppCompatActivity {

    private Spinner spn_faculties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        spn_faculties = findViewById(R.id.spn_add_faculty);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getApplicationContext(),
                        R.array.add_faculty_values,
                        android.R.layout.simple_spinner_dropdown_item);
        spn_faculties.setAdapter(adapter);

    }
}