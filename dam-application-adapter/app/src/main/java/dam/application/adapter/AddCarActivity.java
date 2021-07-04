package dam.application.adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import examen.g1088.stefan.codruta.R;

public class AddCarActivity extends AppCompatActivity {

    public static String EXTRA = "extra";
    Intent intent;

    Button btn_save;
    EditText et_model;

    Spinner spn_year;
    String spinner_option_1 = "2000";
    String spinner_option_2 = "1999";
    String[] options = {spinner_option_1, spinner_option_2};

    CheckBox cb_sh;
    RatingBar rb_rating;

    Car car = new Car();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initComponents();
    }

    private void initComponents() {
        intent = getIntent();

        btn_save = findViewById(R.id.add_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    build();

                    intent.putExtra(EXTRA, car);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


        et_model = findViewById(R.id.add_et);

        spn_year = findViewById(R.id.add_spn);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                options);
        spn_year.setAdapter(adapter);

        cb_sh = findViewById(R.id.add_cb);
        rb_rating = findViewById(R.id.add_rb);

    }

    private boolean validate() {
        if (et_model.getText() == null
                || et_model.getText().toString().isEmpty()) {
            return false;
        }

        return true;
    }

    private void build() {
        String model = et_model.getText().toString();
        car.setModel(model);

        int year = Integer.parseInt(spn_year.getSelectedItem().toString());
        car.setYear(year);

        float rating = rb_rating.getRating();
        car.setRating(rating);

        car.setSecondHand(cb_sh.isChecked());
    }
}