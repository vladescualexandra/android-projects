package eu.ase.ro.seminar7.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import eu.ase.ro.seminar7.R;


public class BankAccountAdapter extends ArrayAdapter<BankAccount> {

    private Context context;
    private List<BankAccount> bankAccounts;
    private LayoutInflater inflater;
    private int resource;

    public BankAccountAdapter(@NonNull Context context, int resource,
                              @NonNull List<BankAccount> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.bankAccounts = objects;
        this.inflater = inflater;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        BankAccount bankAccount = bankAccounts.get(position);
        if (bankAccount != null) {
            addCardHolderName(view, bankAccount.getCardHolderName());
            addBankName(view, bankAccount.getBankName());
            addCardNumber(view, bankAccount.getCardNumber());
            addExpirationPeriod(view, bankAccount.getExpirationMonth(), bankAccount.getExpirationYear());
        }
        return view;
    }

    private void addCardHolderName(View view, String cardHolderName) {
        TextView textView = view.findViewById(R.id.tv_row_card_holder_name);
        populateTextViewContent(textView, cardHolderName);
    }

    private void addBankName(View view, String bankName) {
        TextView textView = view.findViewById(R.id.tv_row_bank_name);
        populateTextViewContent(textView, bankName);
    }

    private void addCardNumber(View view, long cardNumber) {
        TextView textView = view.findViewById(R.id.tv_row_card_number);
        String cardNumberStr = String.valueOf(cardNumber);
        String value = context.getString(R.string.lv_row_view_card_number_format,
                cardNumberStr.substring(0, 4), cardNumberStr.substring(4, 8),
                cardNumberStr.substring(8, 12), cardNumberStr.substring(12, 16));
        populateTextViewContent(textView, value);
    }

    private void addExpirationPeriod(View view, int expirationMonth, int expirationYear) {
        TextView textView = view.findViewById(R.id.tv_row_expiration_time);
        String value = context.getString(R.string.lv_row_view_expiration_time,
                expirationMonth, expirationYear);
        populateTextViewContent(textView, value);
    }

    private void populateTextViewContent(TextView textView, String value) {
        if (value != null && !value.trim().isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.lv_row_view_no_content);
        }
    }
}