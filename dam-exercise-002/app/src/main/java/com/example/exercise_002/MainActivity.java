package com.example.exercise_002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.exercise_002.async.AsyncTaskRunner;
import com.example.exercise_002.async.Callback;
import com.example.exercise_002.network.HttpManager;
import com.example.exercise_002.util.Exam;
import com.example.exercise_002.util.ExamAdapter;
import com.example.exercise_002.util.ExamJSONParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    private static final String URL_ADDRESS = "https://jsonkeeper.com/b/MDEL";
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    private TextView welcome_message;
    private Button btn_login;
    private ListView lv_exams;
    private List<Exam> exams;

    SharedPreferences prefs;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        setAdaper();
        getUsername();
        getExamsFromHttp();
    }

    private void getExamsFromHttp() {
        Callable<String> asyncOperation = new HttpManager(URL_ADDRESS);
        Callback<String> mainThreadOperation = receiveExamsFromHttp();
        asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);
    }

    private Callback<String> receiveExamsFromHttp() {
        return new Callback<String>() {
            @Override
            public void runResultOnUIThread(String result) {
                exams.addAll(ExamJSONParser.fromJson(result));
                notifyAdapter();
            }
        };
    }

    private void initComponents() {
        exams = new ArrayList<>();
        welcome_message = findViewById(R.id.main_welcome_text);
        lv_exams = findViewById(R.id.lv_exams);
        btn_login = findViewById(R.id.main_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getUsername() {
        prefs = getSharedPreferences(LoginActivity.USER_INFO, MODE_PRIVATE);
        username = prefs.getString(LoginActivity.NAME, null);

        if (username != null) {
            welcome_message.setText(getString(R.string.main_welcome_text_format, username));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUsername();
    }

    private void setAdaper() {
        ExamAdapter adapter = new ExamAdapter(getApplicationContext(),
                R.layout.main_row_item,
                exams,
                getLayoutInflater());
        lv_exams.setAdapter(adapter);
    }

    private void notifyAdapter() {
        ExamAdapter adapter = (ExamAdapter) lv_exams.getAdapter();
        adapter.notifyDataSetChanged();
    }
}