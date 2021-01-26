package com.example.dam_seminar_005;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dam_seminar_005.util.DateConverter;
import com.example.dam_seminar_005.util.Expense;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class NewExpenseActivity extends AppCompatActivity {
    public static final String NEW_EXPENSE_KEY = "new_expense_key";

    private TextInputEditText tiet_date;
    private TextInputEditText tiet_amount;
    private TextInputEditText tiet_description;
    private Spinner spn_category;
    private Button btn_save;

    private final DateConverter dateConverter = new DateConverter();
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        initComponents();
    }

    private void initComponents() {
        tiet_date = findViewById(R.id.tiet_new_expense_date);
        tiet_amount = findViewById(R.id.tiet_new_expense_amount);
        tiet_description = findViewById(R.id.tiet_new_expense_description);
        spn_category = findViewById(R.id.spn_new_expense_category);
        btn_save = findViewById(R.id.btn_new_expense_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Expense expense = buildExpenseFromViews();
                    intent.putExtra(NEW_EXPENSE_KEY, expense);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private boolean validate() {
        if (tiet_date.getText() == null || dateConverter.fromString(tiet_date.getText().toString()) == null) {
            Toast.makeText(getApplicationContext(),
                    R.string.invalid_date_field_error,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tiet_amount.getText() == null || tiet_amount.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.invalid_amount_field_error,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Expense buildExpenseFromViews() {
        Date date = dateConverter.fromString(tiet_date.getText().toString());
        Double amount = Double.parseDouble(tiet_amount.getText().toString());
        String description = tiet_description.getText().toString();
        String category = spn_category.getSelectedItem().toString();
        return new Expense(date, category, amount, description);
    }
}
