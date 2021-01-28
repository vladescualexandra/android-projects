package com.example.dam_exam_subject_004;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.dam_exam_subject_004.util.CV;
import com.example.dam_exam_subject_004.util.CVAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 201;
    Button btn_add;
    ListView lv_main;
    List<CV> cvList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.main_add);
        lv_main = findViewById(R.id.main_list);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CVActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        setAdapter();
    }

    private void setAdapter() {
        CVAdapter adapter = new CVAdapter(
                getApplicationContext(),
                R.layout.maint_row_item,
                cvList,
                getLayoutInflater()
        );
        lv_main.setAdapter(adapter);
    }

    private void notifyAdapter() {
        CVAdapter adapter = (CVAdapter) lv_main.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ADD_REQUEST_CODE) {
                CV cv = (CV) data.getSerializableExtra(CVActivity.CV_KEY);
                cvList.add(cv);
                notifyAdapter();
            }
        }
    }
}