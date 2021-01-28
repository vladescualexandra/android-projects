package com.example.dam_exam_subject_004.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dam_exam_subject_004.CVActivity;
import com.example.dam_exam_subject_004.R;

import org.w3c.dom.Text;

import java.util.List;

public class CVAdapter extends ArrayAdapter<CV> {

    private Context context;
    private int resource;
    private List<CV> list;
    private LayoutInflater layoutInflater;

    public CVAdapter(@NonNull Context context,
                     int resource,
                     @NonNull List<CV> list,
                     LayoutInflater layoutInflater) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);

        if (list != null && list.size() > 0) {
            CV cv = list.get(position);

            if (cv != null) {
                buildCV(view, cv);
            }
        }

        return view;
    }

    private void buildCV(View view, CV cv) {

        TextView tv_name = view.findViewById(R.id.main_row_name);
        TextView tv_age = view.findViewById(R.id.main_row_age);
        TextView tv_position = view.findViewById(R.id.main_row_position);
        Button btn_clasificare = view.findViewById(R.id.main_row_clasificare);
        Button btn_editare = view.findViewById(R.id.main_row_editare);

        tv_name.setText(cv.getName());
        tv_age.setText(String.valueOf(cv.getAge()));
        tv_position.setText(cv.getPosition());
        btn_clasificare.setOnClickListener(clasificareEvent(view, cv.getAge()));
        btn_editare.setOnClickListener(editareEvent(cv));
    }

    private View.OnClickListener editareEvent(CV cv) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CVActivity.class);
                intent.putExtra(CVActivity.CV_KEY, cv);
                context.startActivity(intent);
            }
        };
    }

    private View.OnClickListener clasificareEvent(View view, int age) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (age > 40) {
                    view.setBackgroundColor(0xFF00FF00);
                } else if (age == 40) {
                    view.setBackgroundColor(0xFF00FFFF);
                } else {
                    view.setBackgroundColor(0xFFFF0000);
                }
            }
        };
    }
}
