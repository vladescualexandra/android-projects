package com.example.dam_seminar_005;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AccountActivity extends AppCompatActivity {

    public static final String SELECT_ACCOUNT_KEY = "SELECT_ACCOUNT_KEY";

    private RadioGroup rg_options;
    private RadioButton rb_selected;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent intent = getIntent();

        btn_save = findViewById(R.id.account_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_selected = findViewById(rg_options.getCheckedRadioButtonId());
                intent.putExtra(SELECT_ACCOUNT_KEY, rb_selected.getText().toString());
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}