package com.example.dam_seminar_001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_ACTIVITY = "MainActivity";
    public static final String COUNTER_KEY = "counter_key";
    TextView tv_message;
    Button btn_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_message = findViewById(R.id.main_message);
        btn_count = findViewById(R.id.btn_count);

        btn_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = Integer.parseInt(tv_message.getText().toString());
                current++;

                 /* ATENȚIE: Niciodată pe proprietățile de tip text
                 * din componentele vizuale nu setăm valori numerice
                 * (int, long, float, double) fără o conversie
                 * la String.
                 * În caz contrar, Android caută o cheie din strings.xml
                 * și aruncă eroare de execuție atunci când nu o găsește.
                 */

                tv_message.setText(String.valueOf(current));
            }
        });

        Log.i(MAIN_ACTIVITY, "onCreate call");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(MAIN_ACTIVITY, "onPause call");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(MAIN_ACTIVITY, "onResume call");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(MAIN_ACTIVITY, "onDestroy call");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        /*
        * Se apelează înainte de onDestroy.
        * Salvăm infomațiile pe care dorim să le recuperăm ulterior
        */
        outState.putString(COUNTER_KEY, tv_message.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*
        * Se apelează după onCreate.
        * Bundle savedInstanceState == Bundle outState
        * Se citește informația salvată în onSaveInstanceState.
        */
        tv_message.setText(savedInstanceState.getString(COUNTER_KEY));
    }
}