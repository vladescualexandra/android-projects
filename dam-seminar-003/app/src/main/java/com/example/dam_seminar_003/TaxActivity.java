package com.example.dam_seminar_003;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class TaxActivity extends AppCompatActivity {

    private TextInputEditText tiet_account_number;
    private TextInputEditText tiet_amount;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax);

        tiet_account_number = findViewById(R.id.vladescu_alexandra_bianca_tietAccountNumber);
        tiet_amount = findViewById(R.id.vladescu_alexandra_bianca_tietAmount);
        btn_save = findViewById(R.id.vladescu_alexandra_bianca_btnSave);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account_number = tiet_account_number.getText().toString().trim();
                double amount = Double.parseDouble(tiet_amount.getText().toString().trim());

                Toast.makeText(getApplicationContext(),
                        "Account number: " + account_number + "\n"
                                + "Amount: " + amount,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}