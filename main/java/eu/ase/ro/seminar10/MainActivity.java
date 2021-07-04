package eu.ase.ro.seminar10;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import eu.ase.ro.seminar10.asyncTask.Callback;
import eu.ase.ro.seminar10.database.model.Expense;
import eu.ase.ro.seminar10.database.service.ExpenseService;
import eu.ase.ro.seminar10.util.ExpenseAdapter;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_EXPENSE_REQUEST_CODE = 201;
    private static final int UPDATE_EXPENSE_REQUEST_CODE = 202;

    private ListView lvExpenses;
    private FloatingActionButton fabAddExpense;

    private List<Expense> expenses = new ArrayList<>();
    private ExpenseService expenseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        expenseService = new ExpenseService(getApplicationContext());
        expenseService.getAll(getAllFromDbCallback());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Expense expense = (Expense) data.getSerializableExtra(AddExpenseActivity.EXPENSE_KEY);
            if (requestCode == ADD_EXPENSE_REQUEST_CODE) {
                expenseService.insert(insertIntoDbCallback(), expense);
            } else if (requestCode == UPDATE_EXPENSE_REQUEST_CODE) {
                expenseService.update(updateToDbCallback(), expense);
            }
        }
    }

    private void getAllFromDbV2() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                final List<Expense> results = expenseService.getAllV2();
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

    private Callback<List<Expense>> getAllFromDbCallback() {
        return new Callback<List<Expense>>() {
            @Override
            public void runResultOnUiThread(List<Expense> result) {
                if (result != null) {
                    expenses.clear();
                    expenses.addAll(result);
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Expense> insertIntoDbCallback() {
        return new Callback<Expense>() {
            @Override
            public void runResultOnUiThread(Expense result) {
                if (result != null) {
                    expenses.add(result);
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Expense> updateToDbCallback() {
        return new Callback<Expense>() {
            @Override
            public void runResultOnUiThread(Expense result) {
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

    private Callback<Integer> deleteToDbCallback(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if (result != -1) {
                    expenses.remove(position);
                    notifyAdapter();
                }
            }
        };
    }

    private void initComponents() {
        lvExpenses = findViewById(R.id.lv_main_expenses);
        fabAddExpense = findViewById(R.id.fab_main_add_expense);
        addAdapter();
        fabAddExpense.setOnClickListener(addExpenseEventListener());
        lvExpenses.setOnItemClickListener(updateEventListener());
        lvExpenses.setOnItemLongClickListener(deleteEventListener());
    }

    private AdapterView.OnItemClickListener updateEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra(AddExpenseActivity.EXPENSE_KEY, expenses.get(position));
                startActivityForResult(intent, UPDATE_EXPENSE_REQUEST_CODE);
            }
        };
    }

    private AdapterView.OnItemLongClickListener deleteEventListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                expenseService.delete(deleteToDbCallback(position), expenses.get(position));
                return true;
            }
        };
    }

    private View.OnClickListener addExpenseEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddExpenseActivity.class);
                startActivityForResult(intent, ADD_EXPENSE_REQUEST_CODE);
            }
        };
    }

    private void addAdapter() {
        ExpenseAdapter adapter = new ExpenseAdapter(getApplicationContext(), R.layout.lv_expense_row,
                expenses, getLayoutInflater());
        lvExpenses.setAdapter(adapter);
    }

    private void notifyAdapter() {
        ExpenseAdapter adapter = (ExpenseAdapter) lvExpenses.getAdapter();
        adapter.notifyDataSetChanged();
    }
}