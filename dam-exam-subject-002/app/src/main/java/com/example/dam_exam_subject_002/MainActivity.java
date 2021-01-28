package com.example.dam_exam_subject_002;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.dam_exam_subject_002.util.Marriage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 201;
    public static final String MARRIAGE_LIST_KEY = "marriage_list_key";
    Button btn_add;
    Button btn_norm;
    ListView lv_main;
    List<Marriage> marriageList = new ArrayList<>();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.main_add);
        lv_main = findViewById(R.id.main_lv);
        setAdapter();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MarriageActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        btn_norm = findViewById(R.id.main_norm);
        btn_norm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marriageList.size() > 3) {
                    Marriage min = marriageList.get(0);
                    Marriage max = marriageList.get(0);

                    for (int i = 0; i < marriageList.size(); i++) {
                        if (marriageList.get(i).getGuests() > max.getGuests()) {
                            max = marriageList.get(i);
                        }
                        if (marriageList.get(i).getGuests() < min.getGuests()) {
                            min = marriageList.get(i);
                        }
                    }

                    marriageList.remove(min);
                    marriageList.remove(max);
                    notifyAdapter();
                }
            }
        });
    }

    private void setGraphFragment() {
        GraphFragment fragment = new GraphFragment();


        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MARRIAGE_LIST_KEY, (ArrayList<? extends Parcelable>) marriageList);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == ADD_REQUEST_CODE
                && data != null) {
            Marriage marriage = (Marriage) data.getSerializableExtra(MarriageActivity.MARRIAGE_KEY);
            marriageList.add(marriage);
            notifyAdapter();
        }
    }

    private void setAdapter() {
        ArrayAdapter<Marriage> adapter = new ArrayAdapter<Marriage>
                (getApplicationContext(), android.R.layout.simple_list_item_1, marriageList);
        lv_main.setAdapter(adapter);
    }

    private void notifyAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lv_main.getAdapter();
        adapter.notifyDataSetChanged();
        setGraphFragment();
    }
}