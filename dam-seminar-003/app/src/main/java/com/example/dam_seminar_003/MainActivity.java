package com.example.dam_seminar_003;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dam_seminar_003.util.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_STUDENT_REQUEST_CODE = 201;
    private FloatingActionButton fab_add;
    private FloatingActionButton fab_tax;
    private ListView lv_students;
    private List<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        setListViewAdapter();
    }

    private void initComponents() {
        fab_add = findViewById(R.id.fab_main_add);
        fab_tax = findViewById(R.id.fab_main_tax);
        lv_students = findViewById(R.id.lv_main_students);

        fab_add.setOnClickListener(addRequestStudentClickEvent());
        fab_tax.setOnClickListener(addOpenTaxClickEvent());
    }

    private void setListViewAdapter() {
        /*
        * Inițializare ArrayAdapter de tip Student pentru adăugare
        * în ListView.
        */
        ArrayAdapter<Student> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                students);
        lv_students.setAdapter(adapter);
    }

    private void notifyAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lv_students.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener addRequestStudentClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, ADD_STUDENT_REQUEST_CODE);
            }
        };
    }

    private View.OnClickListener addOpenTaxClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TaxActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_STUDENT_REQUEST_CODE
                && resultCode == RESULT_OK && data != null) {
            Student student = (Student) data.getSerializableExtra(AddActivity.STUDENT_KEY);
            if (student != null) {
                students.add(student);
                notifyAdapter();
            }
        }
    }
}