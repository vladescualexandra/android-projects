package eu.ase.ro.seminar10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import eu.ase.ro.seminar10.database.model.Expense;
import eu.ase.ro.seminar10.util.DateConverter;

public class AddExpenseActivity extends AppCompatActivity {

    public static final String EXPENSE_KEY = "expenseKey";
    private TextInputEditText tietDate;
    private TextInputEditText tietAmount;
    private Spinner spnCategory;
    private TextInputEditText tietDescription;
    private ImageButton ibSave;

    private Intent intent;
    private Expense expense = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        initComponents();
        intent = getIntent();
        if (intent.hasExtra(EXPENSE_KEY)) {
            expense = (Expense) intent.getSerializableExtra(EXPENSE_KEY);
            buildViewsFromExpense(expense);
        }
    }

    private void buildViewsFromExpense(Expense expense) {
        if (expense == null) {
            return;
        }
        if (expense.getDate() != null) {
            tietDate.setText(DateConverter.fromDate(expense.getDate()));
        }
        if (expense.getAmount() != null) {
            tietAmount.setText(String.valueOf(expense.getAmount()));
        }
        tietDescription.setText(expense.getDescription());
        selectCategory(expense);
    }

    private void selectCategory(Expense expense) {
        ArrayAdapter adapter = (ArrayAdapter) spnCategory.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            String item = (String) adapter.getItem(i);
            if (item != null && item.equals(expense.getCategory())) {
                spnCategory.setSelection(i);
                break;
            }
        }
    }

    private void initComponents() {
        tietDate = findViewById(R.id.tiet_add_expense_date);
        tietAmount = findViewById(R.id.tiet_add_expense_amount);
        spnCategory = findViewById(R.id.spn_add_expense_category);
        tietDescription = findViewById(R.id.tiet_add_expense_description);
        ibSave = findViewById(R.id.ibtn_add_expense_save);
        addCategoryAdapter();
        ibSave.setOnClickListener(saveExpenseEventListener());
    }

    private View.OnClickListener saveExpenseEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    createFromViews();
                    intent.putExtra(EXPENSE_KEY, expense);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private void createFromViews() {
        Date date = DateConverter.fromString(tietDate.getText().toString());
        String category = spnCategory.getSelectedItem().toString();
        Double amount = Double.parseDouble(tietAmount.getText().toString());
        String description = tietDescription.getText().toString();
        if (expense == null) {
            expense = new Expense(date, category, amount, description);
        } else {
            expense.setDate(date);
            expense.setAmount(amount);
            expense.setCategory(category);
            expense.setDescription(description);
        }
    }

    private void addCategoryAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.add_expense_category_values,
                android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
    }

    private boolean validate() {
        if (tietDate.getText() == null || tietDate.getText().toString().trim().isEmpty()
                || DateConverter.fromString(tietDate.getText().toString()) == null) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_expense_date_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietAmount.getText() == null || tietAmount.getText().toString().isEmpty()
                || Double.parseDouble(tietAmount.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_expense_amount_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }
}