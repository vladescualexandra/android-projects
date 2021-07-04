package dam.application.graph;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<Wedding> weddingList = new ArrayList<>();
    public static final int REQUEST_CODE_ADD = 201;
    public static final String LIST_KEY = "weddings";
    public static final String DATE_KEY = "date";
    Button btn_add;

    Button btn_normalizare;
    DatePicker dp_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intiComponents();
    }

    private void intiComponents() {
        btn_add = findViewById(R.id.main_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddWeddingActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        btn_normalizare = findViewById(R.id.main_normalizare);
        dp_date = findViewById(R.id.main_date);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dp_date.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    setGraphFragment();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && data != null) {
            Wedding wedding = (Wedding) data.getSerializableExtra(AddWeddingActivity.EXTRA_WEDDING);
            if (requestCode == REQUEST_CODE_ADD) {
                weddingList.add(wedding);
            }
        }
    }

    private void setGraphFragment() {
        GraphFragment fragment = new GraphFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_KEY, (ArrayList<? extends Parcelable>) weddingList);
        bundle.putString(DATE_KEY, Util.fromDatePicker(dp_date));
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .commit();
    }



}