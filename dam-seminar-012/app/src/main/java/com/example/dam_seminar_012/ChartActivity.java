package com.example.dam_seminar_012;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.dam_seminar_012.util.ChartView;
import com.example.dam_seminar_012.util.Coach;

import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {

    public static final String COACHES_KEY = "COACHES_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Coach> coaches = (List<Coach>) getIntent().getSerializableExtra(COACHES_KEY);
        Map<String, Integer> source = getSource(coaches);

        setContentView(new ChartView(getApplicationContext(), source));
    }

    private Map<String, Integer> getSource(List<Coach> coaches) {
        if (coaches == null || coaches.isEmpty()) {
            return null;
        } else {
            Map<String, Integer> source = new HashMap<>();

            for (Coach coach : coaches) {
                if (source.containsKey(coach.getRole())) {
                    Integer currentValue = source.get(coach.getRole());
                    Integer newValue = (currentValue != null ? currentValue : 0) + 1;
                    source.put(coach.getRole(), newValue);
                } else {
                    source.put(coach.getRole(), 1);
                }
            }

            return source;
        }
    }
}