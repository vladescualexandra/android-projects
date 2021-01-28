package com.example.dam_exam_subject_003;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.dam_exam_subject_003.async.Callback;
import com.example.dam_exam_subject_003.util.Website;
import com.example.dam_exam_subject_003.util.WebsiteService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 201;
    Button btn_add;
    ListView lv_main;

    RadioGroup rg_filter;

    List<Website> websites = new ArrayList<>();
    WebsiteService websiteService;

    EditText et_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.main_add);
        lv_main = findViewById(R.id.main_list);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebsiteActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        ArrayAdapter<Website> adapter = new ArrayAdapter<Website>
                (getApplicationContext(), android.R.layout.simple_list_item_1, websites);
        lv_main.setAdapter(adapter);

        websiteService = new WebsiteService(getApplicationContext());
        websiteService.getAll(getAllFromDbCallback());

        rg_filter = findViewById(R.id.rg_filter);
        rg_filter.setOnCheckedChangeListener(filterEvent());

        et_filter = findViewById(R.id.main_filter);
        et_filter.setOnClickListener(filter2Event());
    }

    private View.OnClickListener filter2Event() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et_filter.getText().toString().trim();
                if (text != null && text.length() > 3) {
                    websiteService.filter(filterCallback(), text);
                } else {
                    websiteService.getAll(getAllFromDbCallback());
                }
            }
        };
    }

    private Callback<List<Website>> filterCallback() {
        return new Callback<List<Website>>() {
            @Override
            public void runResultOnUIThread(List<Website> result) {
                if (result != null) {
                    websites.clear();
                    websites.addAll(result);
                    notifyAdapter();
                }
            }
        };
    }


    private Callback<List<Website>> getAllFromDbCallback() {
        return new Callback<List<Website>>() {
            @Override
            public void runResultOnUIThread(List<Website> result) {
                if (result != null) {
                    websites.clear();
                    websites.addAll(result);
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Website> insertIntoDbCallback() {
        return new Callback<Website>() {
            @Override
            public void runResultOnUIThread(Website result) {
                if (result != null) {
                    websites.add(result);
                    notifyAdapter();
                }
            }
        };
    }

    private RadioGroup.OnCheckedChangeListener filterEvent() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.fiter_alfabetic) {
                    alfabetic();
                } else if (checkedId == R.id.filter_invers_alfabetic) {
                    alfabetic();
                    Collections.reverse(websites);
                } else if (checkedId == R.id.filter_crescator) {
                    crescator();
                } else if (checkedId == R.id.filter_descrescator) {
                    crescator();
                    Collections.reverse(websites);
                } else if (checkedId == R.id.filter_alfabetic_crescator) {
                    alfabetic();
                    crescator();
                } else if (checkedId == R.id.fiter_alfabetic_descrescator) {
                    crescator();
                    Collections.reverse(websites);
                    alfabetic();
                }
                notifyAdapter();
            }
        };
    }

    private void crescator() {
        Collections.sort(websites, new Comparator<Website>() {
            @Override
            public int compare(Website o1, Website o2) {
                if (o1.getSpan() > o2.getSpan()) return 1;
                else if (o1.getSpan() == o2.getSpan())
                    return 0;
                else return -1;
            }
        });
    }

    private void alfabetic() {
        Collections.sort(websites, new Comparator<Website>() {
            @Override
            public int compare(Website o1, Website o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    private void notifyAdapter() {
        ArrayAdapter<Website> adapter = (ArrayAdapter) lv_main.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ADD_REQUEST_CODE) {
                Website website = (Website) data.getSerializableExtra(WebsiteActivity.WEBSITE_KEY);
                websiteService.insert(insertIntoDbCallback(), website);
                notifyAdapter();
            }
        }
    }
}