package com.example.dam_exercise_004;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.dam_exercise_004.async.Callback;
import com.example.dam_exercise_004.database.model.BusRide;
import com.example.dam_exercise_004.database.service.BusRideService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_BUS_RIDE_REQUEST_CODE = 201;
    Button btn_add;
    ListView lv_main;
    List<BusRide> busRides;
    private BusRideService busRideService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        btn_add = findViewById(R.id.main_btn_add);
        lv_main = findViewById(R.id.main_lv);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, ADD_BUS_RIDE_REQUEST_CODE);
            }
        });

        busRides = new ArrayList<>();
        setAdapter();

        busRideService = new BusRideService(getApplicationContext());
        busRideService.getAll(getAllBusRidesFromDBCallback());
    }


    private Callback<List<BusRide>> getAllBusRidesFromDBCallback() {
        return new Callback<List<BusRide>>() {
            @Override
            public void runResultOnUIThread(List<BusRide> result) {
                if (result != null) {
                    busRides.clear();
                    busRides.addAll(result);
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<BusRide> insertBusRideToDBCallback() {
        return new Callback<BusRide>() {
            @Override
            public void runResultOnUIThread(BusRide result) {
                if (result != null) {
                    busRides.add(result);
                    notifyAdapter();
                }
            }
        };
    }



    private void setAdapter() {
        ArrayAdapter<BusRide> adapter = new ArrayAdapter<BusRide>
                (getApplicationContext(), android.R.layout.simple_list_item_1, busRides);
        lv_main.setAdapter(adapter);

    }

    private void notifyAdapter() {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter) lv_main.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
            && requestCode == ADD_BUS_RIDE_REQUEST_CODE && data != null) {
            BusRide br = (BusRide) data.getSerializableExtra(AddActivity.RIDE_KEY);
            busRideService.insert(br, insertBusRideToDBCallback());

        }
    }
}