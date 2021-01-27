package com.example.dam_exercise_004;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import com.example.dam_exercise_004.database.model.BusRide;
import com.google.android.material.textfield.TextInputEditText;

public class AddActivity extends AppCompatActivity {

    public static final String RIDE_KEY = "ride_key";
    TextInputEditText tiet_destination;
    TextInputEditText tiet_price;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = getIntent();

        tiet_destination = findViewById(R.id.add_destination);
        tiet_price = findViewById(R.id.add_price);
        btn_save = findViewById(R.id.add_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = tiet_destination.getText().toString().trim();
                int price = Integer.parseInt(tiet_price.getText().toString().trim());
                BusRide br = new BusRide(destination, price);
                intent.putExtra(RIDE_KEY, br);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}