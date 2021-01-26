package com.example.exercise_002.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exercise_002.R;

import java.util.List;

public class ExamAdapter extends ArrayAdapter<Exam> {

    Context context;
    int resource;
    List<Exam> exams;
    LayoutInflater layoutInflater;


    public ExamAdapter(@NonNull Context context,
                       int resource,
                       List<Exam> exams,
                       LayoutInflater layoutInflater) {
        super(context, resource, exams);
        this.context = context;
        this.resource = resource;
        this.exams = exams;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Exam exam = exams.get(position);

        if (exam != null) {
            buildExam(view, exam);
        }

        return view;
    }

    private void buildExam(View view, Exam exam) {
        TextView tv_course = view.findViewById(R.id.exam_course);
        tv_course.setText(exam.getCourse());

        TextView tv_date = view.findViewById(R.id.exam_date);
        tv_date.setText(exam.getDate().toString());

        TextView tv_classroom = view.findViewById(R.id.exam_classroom);
        tv_classroom.setText(exam.getClassroom());

        TextView tv_supervisor = view.findViewById(R.id.exam_supervisor);
        tv_supervisor.setText(exam.getSupervisor());
    }
}
