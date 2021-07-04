package eu.ase.ro.seminar10.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import eu.ase.ro.seminar10.R;
import eu.ase.ro.seminar10.database.model.Expense;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    private Context context;
    private int resource;
    private List<Expense> expenses;
    private LayoutInflater inflater;

    public ExpenseAdapter(@NonNull Context context, int resource, @NonNull List<Expense> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.expenses = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Expense expense = expenses.get(position);
        if (expense != null) {
            addDate(view, expense.getDate());
            addCategory(view, expense.getCategory());
            addAmount(view, expense.getAmount());
        }
        return view;
    }

    private void addDate(View view, Date date) {
        TextView textView = view.findViewById(R.id.tv_lv_expense_row_date);
        addTextViewContent(textView, DateConverter.fromDate(date));
    }

    private void addCategory(View view, String category) {
        TextView textView = view.findViewById(R.id.tv_lv_expense_row_category);
        addTextViewContent(textView, category);
    }

    private void addAmount(View view, Double amount) {
        TextView textView = view.findViewById(R.id.tv_lv_expense_row_amount);
        String value = null;
        if (amount != null) {
            value = context.getString(R.string.lv_expense_row_amount_value, amount.toString());
        }
        addTextViewContent(textView, value);
    }

    private void addTextViewContent(TextView textView, String value) {
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.lv_expense_row_default_value);
        }
    }
}
