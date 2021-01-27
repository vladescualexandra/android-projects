package com.example.dam_seminar_010;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dam_seminar_010.async.Callback;
import com.example.dam_seminar_010.database.model.Expense;
import com.example.dam_seminar_010.database.service.ExpenseService;
import com.example.dam_seminar_010.util.ExpenseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_EXPENSE_REQUEST_CODE = 201;
    private static final int UPDATE_EXPENSE_REQUEST_CODE = 202;

    private ListView lv_main;
    private FloatingActionButton fab_add;
    private ExpenseService expenseService;
    private List<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

    }

    /************************** INIT **************************/
    private void initComponents() {
        fab_add = findViewById(R.id.fab_main_add_expense);
        fab_add.setOnClickListener(addExpenseEvent());

        lv_main = findViewById(R.id.lv_main_expenses);
        expenses = new ArrayList<>();
        setAdapter();

        lv_main.setOnItemClickListener(updateExpenseEvent());
        lv_main.setOnItemLongClickListener(deleteExpenseEvent());

        expenseService = new ExpenseService(getApplicationContext());
        expenseService.getAll(getAllFromDBCallback());
    }

    private void setAdapter() {
        ExpenseAdapter adapter = new ExpenseAdapter(
                getApplicationContext(),
                R.layout.main_row_item,
                expenses,
                getLayoutInflater()
        );
        lv_main.setAdapter(adapter);
    }

    private void notifyAdapter() {
        ExpenseAdapter adapter = (ExpenseAdapter) lv_main.getAdapter();
        adapter.notifyDataSetChanged();
    }


    /************************** EVENT LISTENERS **************************/
    private View.OnClickListener addExpenseEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        AddExpenseActivity.class);
                startActivityForResult(intent, ADD_EXPENSE_REQUEST_CODE);
            }
        };
    }

    private AdapterView.OnItemClickListener updateExpenseEvent() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),
                        AddExpenseActivity.class);
                intent.putExtra(AddExpenseActivity.EXPENSE_KEY, expenses.get(position));
                startActivityForResult(intent, UPDATE_EXPENSE_REQUEST_CODE);
            }
        };
    }

    private AdapterView.OnItemLongClickListener deleteExpenseEvent() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                expenseService.delete(deleteFromDBCallback(position), expenses.get(position));
                return true;
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Expense expense = (Expense) data.getSerializableExtra(AddExpenseActivity.EXPENSE_KEY);
            if (requestCode == ADD_EXPENSE_REQUEST_CODE) {
                expenseService.insert(insertIntoDBCallback(), expense);
            } else if (requestCode == UPDATE_EXPENSE_REQUEST_CODE) {
                expenseService.update(updateIntoDBCallback(), expense);
            }
        }
    }

    private void getAllFromDBWithoutCallback() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                final List<Expense> results = expenseService.getAllWithoutCallback();
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (results != null) {
                            expenses.clear();
                            expenses.addAll(results);
                            notifyAdapter();
                        }
                    }
                });
            }
        };
        thread.start();
    }

    /************************** CALLBACKS **************************/
    private Callback<List<Expense>> getAllFromDBCallback() {
        return new Callback<List<Expense>>() {
            @Override
            public void runResultOnUIThread(List<Expense> result) {
                if (result != null) {
                    expenses.clear();
                    expenses.addAll(result);
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Expense> insertIntoDBCallback() {
        return new Callback<Expense>() {
            @Override
            public void runResultOnUIThread(Expense result) {
                if (result != null) {
                    expenses.add(result);
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Expense> updateIntoDBCallback() {
        return new Callback<Expense>() {
            @Override
            public void runResultOnUIThread(Expense result) {
                if (result != null) {
                    for (Expense expense : expenses) {
                        if (expense.getId() == result.getId()) {
                            expense.setDate(result.getDate());
                            expense.setAmount(result.getAmount());
                            expense.setCategory(result.getCategory());
                            expense.setDescription(result.getDescription());
                            break;
                        }
                    }
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Integer> deleteFromDBCallback(int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUIThread(Integer result) {
                if (result != -1) {
                    expenses.remove(position);
                    notifyAdapter();
                }
            }
        };
    }


}