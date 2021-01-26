package com.example.dam_seminar_003;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.dam_seminar_003.util.DateConverter;
import com.example.dam_seminar_003.util.Student;
import com.example.dam_seminar_003.util.StudyType;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class AddActivity extends AppCompatActivity {

    public static final String STUDENT_KEY = "student_key";
    private final DateConverter dateConverter = new DateConverter();

    private TextInputEditText tiet_name;
    private TextInputEditText tiet_age;
    private TextInputEditText tiet_enrollment;
    private RadioGroup rg_study_type;
    private Spinner spn_faculties;
    private Button btn_save;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initComponents();
    }

    private void initComponents() {
        intent = getIntent();
        tiet_name = findViewById(R.id.tiet_add_name);
        tiet_age = findViewById(R.id.tiet_add_age);
        tiet_enrollment = findViewById(R.id.tiet_add_enrollment_date);
        rg_study_type = findViewById(R.id.rg_add_study_type);
        spn_faculties = findViewById(R.id.spn_add_faculties);
        populateSpinner();

        btn_save = findViewById(R.id.btn_add_save);
        btn_save.setOnClickListener(addSaveClickEvent());
    }

    private void populateSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getApplicationContext(),
                        R.array.add_faculties,
                        android.R.layout.simple_spinner_dropdown_item);
        spn_faculties.setAdapter(adapter);
    }

    private View.OnClickListener addSaveClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Student student = buildStudent();
                    intent.putExtra(STUDENT_KEY, student);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private boolean validate() {
        if (tiet_name.getText() == null
                || tiet_name.getText().toString().trim().length() < 3) {
            return false;
        }
        if (tiet_age.getText() == null
            || Integer.parseInt(tiet_age.getText().toString().trim()) < 0
            || Integer.parseInt(tiet_age.getText().toString().trim()) > 100) {
            return false;
        }
        if (tiet_enrollment.getText() == null
            || dateConverter.fromString(tiet_enrollment.getText().toString().trim()) == null) {
            return false;
        }
        return true;
    }

    private Student buildStudent() {
        String name = tiet_name.getText().toString();
        int age = Integer.parseInt(tiet_age.getText().toString().trim());
        Date enrollment = dateConverter.fromString(tiet_enrollment.getText().toString());
        StudyType studyType = StudyType.FULL_TIME;
        if (rg_study_type.getCheckedRadioButtonId() == R.id.rb_add_study_type_distance) {
            studyType = StudyType.DISTANCE;
        }
        String faculty = spn_faculties.getSelectedItem().toString();
        return new Student(name, age, enrollment, studyType, faculty);
    }


}