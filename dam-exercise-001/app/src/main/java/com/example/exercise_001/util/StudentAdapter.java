package com.example.exercise_001.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exercise_001.R;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    private Context context;
    private int resource;
    private List<Student> studentList;
    private LayoutInflater layoutInflater;

    public StudentAdapter(@NonNull Context context,
                          int resource,
                          @NonNull List<Student> studentList,
                          LayoutInflater layoutInflater) {
        super(context, resource, studentList);
        this.context = context;
        this.resource = resource;
        this.studentList = studentList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        Student student = studentList.get(position);
        View view = layoutInflater.inflate(resource, parent, false);

        if (student != null) {
            buildView(view, student);
        }

        return view;
    }

    private void buildView(View view, Student student) {

        TextView nume = view.findViewById(R.id.student_name);
        TextView an = view.findViewById(R.id.student_an);
        TextView serie = view.findViewById(R.id.student_serie);
        TextView grupa = view.findViewById(R.id.student_grupa);
        Spinner facultate = view.findViewById(R.id.student_facultate);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(context,
                        R.array.facultati,
                        android.R.layout.simple_spinner_dropdown_item);
        facultate.setAdapter(adapter);
        facultate.setEnabled(false);

        nume.setText(student.getNume());
        an.setText(String.valueOf(student.getAn()));
        serie.setText(student.getSerie());
        grupa.setText(String.valueOf(student.getGrupa()));

        int id = 0;
        switch (student.getFacultate()) {
            case Student.FACULTATE_CSIE:
                id = 0;
                break;
            case Student.FACULTATE_CIG:
                id = 1;
                break;
            case Student.FACULTATE_MRK:
                id = 2;
                break;
        }
        facultate.setSelection(id);
    }
}
