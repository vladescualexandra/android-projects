package com.example.dam_exercise_003;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    public static final String USER_KEY = "user_key";
    public static final String NAME = "name";
    public static final String POSITION = "position";
    private TextView tv_name;
    private TextView tv_position;
    private Button btn_save;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv_name = findViewById(R.id.profile_name);
        tv_position = findViewById(R.id.profile_functie);
        btn_save = findViewById(R.id.profile_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tv_name.getText().toString().trim();
                String position = tv_position.getText().toString().trim();

                prefs = getSharedPreferences(USER_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(NAME, name);
                editor.putString(POSITION, position);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}