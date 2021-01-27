package com.example.dam_exam_subject_001;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.dam_exam_subject_001.util.Bid;
import com.example.dam_exam_subject_001.util.State;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BidActivity extends AppCompatActivity {

    public static final String BID_KEY = "bid_key";
    TextInputEditText tiet_name;
    RadioGroup rg_state;
    Spinner spn_object;
    DatePicker datePicker;
    TimePicker timePicker;
    CheckBox cb_registered;
    SeekBar sb_bid;
    Button btn_save;

    Intent intent;
    Bid bid;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid);

        intent = getIntent();
        initComponents();

        if (intent.hasExtra(BID_KEY)) {
            Bid bid = (Bid) intent.getSerializableExtra(BID_KEY);
            if (bid != null) {
                buildViews(bid);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void buildViews(Bid bid) {
        tiet_name.setText(bid.getName());

        if (bid.getState() == State.CLOSED) {
            rg_state.check(R.id.bid_state_closed);
        } else {
            rg_state.check(R.id.bid_state_in_progress);
        }

        // TODO Spinner
        // TODO datePicker
        // TODO timePicker

        cb_registered.setChecked(bid.isRegistered());
        sb_bid.setProgress(bid.getBid());
    }

    private void initComponents() {
        tiet_name = findViewById(R.id.bid_name);
        rg_state = findViewById(R.id.bid_state);
        spn_object = findViewById(R.id.bid_object);
        datePicker = findViewById(R.id.bid_date);
        timePicker = findViewById(R.id.bid_time);
        cb_registered = findViewById(R.id.bid_registered);
        sb_bid = findViewById(R.id.bid_bid);
        btn_save = findViewById(R.id.bid_save);

        setSpinnerAdapter();
        btn_save.setOnClickListener(saveEvent());
    }

    private View.OnClickListener saveEvent() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (validate()) {
                    bid = buildBid();

                    intent.putExtra(BID_KEY, bid);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Bid buildBid() {
        String name = tiet_name.getText().toString().trim();

        State state = State.IN_PROGRESS;
        if (rg_state.getCheckedRadioButtonId() == R.id.bid_state_closed) {
            state = State.CLOSED;
        }

        String object = spn_object.getSelectedItem().toString();


        Date date = Bid.fromDatePicker(datePicker);


        int hh = timePicker.getHour();
        int mm = timePicker.getMinute();

        String time = Bid.convertTime(hh, mm);

        boolean registered = cb_registered.isChecked();

        int bid = sb_bid.getProgress();

        return new Bid(name, state, object, date, time, registered, bid);
    }

    private boolean validate() {
        if (tiet_name.getText().toString().length() < 3)
            return false;
        return true;
    }

    private void setSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getApplicationContext(),
                        R.array.objects,
                        R.layout.support_simple_spinner_dropdown_item);
        spn_object.setAdapter(adapter);
    }


}