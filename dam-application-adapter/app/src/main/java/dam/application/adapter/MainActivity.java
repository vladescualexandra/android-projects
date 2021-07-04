package dam.application.adapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import examen.g1088.stefan.codruta.R;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_CODE_ADD = 201;
    List<Car> list = new ArrayList<>();

    Button btn_add;
    ListView lv_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        btn_add = findViewById(R.id.main_add);
        btn_add.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddCarActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });

        lv_main = findViewById(R.id.main_lv);
        setAdapter();
    }

    private void setAdapter() {
        CarAdapter adapter = new CarAdapter(MainActivity.this,
                R.layout.row_lv_cars,
                list,
                getLayoutInflater());
        lv_main.setAdapter(adapter);
    }

    private void notifyAdapter() {
        CarAdapter adapter = (CarAdapter) lv_main.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Car car = (Car) data.getSerializableExtra(AddCarActivity.EXTRA);
            if (requestCode == REQUEST_CODE_ADD) {
                list.add(car);
                notifyAdapter();
            }
        }
    }
}