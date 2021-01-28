package com.example.dam_exam_subject_004;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.example.dam_exam_subject_004.util.CV;

import java.util.Arrays;

public class CVActivity extends AppCompatActivity {

    public static final String CV_KEY = "cv_key";
    private String name; // EditText
    private String position; // Spinner
    private int age; // SeekBar
    private String degree; // RadioGroup
    private boolean interview; // CheckBox

    EditText et_name;
    Spinner spn_position;
    SeekBar sb_age;
    RadioGroup rg_degree;
    CheckBox cb_interview;
    Button btn_save;

    Intent intent;
    CV cv;
    CV oldCv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_v);

        intent = getIntent();
        et_name = findViewById(R.id.cv_name);
        spn_position = findViewById(R.id.cv_position);
        sb_age = findViewById(R.id.cv_age);
        rg_degree = findViewById(R.id.cv_degree);
        cb_interview = findViewById(R.id.cv_interview);
        btn_save = findViewById(R.id.cv_save);

        setAdapter();


        if (intent.hasExtra(CV_KEY)) {
            oldCv = (CV) intent.getSerializableExtra(CV_KEY);
            buildViews(oldCv);
        }


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_name.getText().toString().trim().length() < 3) {
                    et_name.setError(getString(R.string.invalid_name));
                } else {
                    name = et_name.getText().toString().trim();
                    position = spn_position.getSelectedItem().toString();
                    age = sb_age.getProgress();
                    degree = (rg_degree.getCheckedRadioButtonId() == R.id.cv_degree_master) ? CV.DEGREE_MASTER : CV.DEGREE_BACHELOR;
                    interview = cb_interview.isChecked();

                    cv = new CV(name, position, age, degree, interview);

                    if (intent.hasExtra(CV_KEY)) {
                        if (!cv.equals(oldCv)) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(CVActivity.this);
                            dialog.setTitle("Do you want to change the CV?");
                            dialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    intent.putExtra(CV_KEY, cv);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            });
                            dialog.setNegativeButton(R.string.no, null);
                            dialog.show();
                        } else {
                            finish();
                        }
                    } else {
                        intent.putExtra(CV_KEY, cv);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });

    }

    private void buildViews(CV cv) {
        et_name.setText(cv.getName());
        spn_position.setSelection(Arrays.asList(CV.POSITIONS).indexOf(cv.getPosition()));
        sb_age.setProgress(cv.getAge());
        if (cv.getDegree().equals(CV.DEGREE_BACHELOR)) {
            rg_degree.check(R.id.cv_degree_bachelor);
        } else {
            rg_degree.check(R.id.cv_degree_master);
        }
        cb_interview.setChecked(cv.isInterview());
    }

    private void setAdapter() {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>
                (getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, CV.POSITIONS);
        spn_position.setAdapter(adapter);
    }
}