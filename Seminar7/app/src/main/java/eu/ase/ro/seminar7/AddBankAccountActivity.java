package eu.ase.ro.seminar7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import eu.ase.ro.seminar7.util.BankAccount;

public class AddBankAccountActivity extends AppCompatActivity {
    public static final String BANK_ACCOUNT_KEY = "bank_account_key";
    //declarare componente vizuale
    private TextInputEditText tietCardHolderName;
    private TextInputEditText tietCardNumber;
    private TextInputEditText tietExpirationMonth;
    private TextInputEditText tietExpirationYear;
    private Spinner spnBankName;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_account);
        initComponents();
        //tramsfer de parametri - pasul 2 preluare intent definit in activitatea apelatoare
        intent = getIntent();
    }

    //initializare variabile java corespunzatoare componentelor vizuale
    private void initComponents() {
        tietCardHolderName = findViewById(R.id.tiet_add_bank_account_card_holder_name);
        tietCardNumber = findViewById(R.id.tiet_add_bank_account_card_number);
        tietExpirationMonth = findViewById(R.id.tiet_add_bank_account_expiration_month);
        tietExpirationYear = findViewById(R.id.tiet_add_bank_account_expiration_year);
        spnBankName = findViewById(R.id.spn_add_bank_account_bank_name);
        btnSave = findViewById(R.id.btn_add_bank_account_save);
        populateBankNameAdapter();
        btnSave.setOnClickListener(addSaveEventListener());
    }

    private View.OnClickListener addSaveEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    BankAccount bankAccount = createBankAccountFromViews();
                    //transfer de parametri intre activitati - VEZI Seminar 3
                    //(pasul 3 din schema - intoarcerea rezultatului catre apelator)
                    intent.putExtra(BANK_ACCOUNT_KEY, bankAccount);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private boolean validate() {
        //validare campul cardHolderName sa contina minim 3 caractere diferite de spatii goale
        if (tietCardHolderName.getText() == null
                || tietCardHolderName.getText().toString().trim().length() < 3) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_bank_account_card_holder_name_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        //validare campul Card Number sa contina fix 16 caractere
        if (tietCardNumber.getText() == null
                || tietCardNumber.getText().toString().trim().length() != 16) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_bank_account_card_number_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        //validare campul Expiration Month sa contina un numar in [1,12]
        if (tietExpirationMonth.getText() == null
                || tietExpirationMonth.getText().toString().trim().length() == 0
                || Integer.parseInt(tietExpirationMonth.getText().toString()) < 1
                || Integer.parseInt(tietExpirationMonth.getText().toString()) > 12) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_bank_account_card_expiration_month_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        //validare campul Expiration Year sa contina o valoare de fix 4 caractere
        if (tietExpirationYear.getText() == null
                || tietExpirationYear.getText().toString().trim().length() != 4) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_bank_account_expiration_year_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    private BankAccount createBankAccountFromViews() {
        //preluare informatii din componentele vizuale pentru a construi un obiect de tip BankAccount
        String cardHolderName = tietCardHolderName.getText().toString();
        long cardNumber = Long.parseLong(tietCardNumber.getText().toString());
        int expirationMonth = Integer.parseInt(tietExpirationMonth.getText().toString());
        int expirationYear = Integer.parseInt(tietExpirationYear.getText().toString());
        String bankName = spnBankName.getSelectedItem().toString();
        return new BankAccount(cardHolderName, cardNumber, expirationMonth, expirationYear, bankName);
    }

    private void populateBankNameAdapter() {
        //populare spinner cu adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.bank_names,
                android.R.layout.simple_spinner_dropdown_item);
        spnBankName.setAdapter(adapter);
    }
}