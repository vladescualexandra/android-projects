package eu.ase.ro.seminar7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import eu.ase.ro.seminar7.asyncTask.AsyncTaskRunner;
import eu.ase.ro.seminar7.asyncTask.Callback;
import eu.ase.ro.seminar7.network.HttpManager;
import eu.ase.ro.seminar7.util.BankAccount;
import eu.ase.ro.seminar7.util.BankAccountAdapter;
import eu.ase.ro.seminar7.util.BankAccountJsonParser;


public class MainActivity extends AppCompatActivity {
    private static final int ADD_BANK_ACCOUNT_REQUEST_CODE = 210;
    public static final String BANK_ACCOUNTS_URL = "https://jsonkeeper.com/b/DHQ3";

    //declarare componente vizuale
    private FloatingActionButton fabAddBankAccount;
    private ListView lvBankAccounts;

    private List<BankAccount> bankAccounts = new ArrayList<>();
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        getBankAccountsFromHttp();
    }

    private void getBankAccountsFromHttp() {
        //definim un obiect de tip Callable pe care dorim sa-l procesam pe un alt fir de executie (RunnableTask)
        //HttpManager implementeaza aceasta interfata.
        Callable<String> asyncOperation = new HttpManager(BANK_ACCOUNTS_URL);
        //definim Callback-ul, adica zona din activitatea unde dorim sa receptionam rezultatul procesarii paralele
        //realizata de Callable
        Callback<String> mainThreadOperation = receiveBankAccountsFromHttp();
        //Apelam asyncTaskRunner cu operation asincrona si zona de cod din activitate unde dorim sa primim raspunsul
        asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);
    }

    private Callback<String> receiveBankAccountsFromHttp() {
        return new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                //apelam parsatorul de json, iar rezultatul obtinut il adaugam in lista de obiecte BankAccount
                //existenta la nivelul activitati
                bankAccounts.addAll(BankAccountJsonParser.fromJson(result));
                //avand in vedere ca lista de obiecte este modificata la linia de mai sus,
                // este necesar sa notificam adapterul de acest lucru astfel incat obiectele noi
                //sa fie incarcate in ListView
                notifyAdapter();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_BANK_ACCOUNT_REQUEST_CODE
                && resultCode == RESULT_OK && data != null) {
            BankAccount bankAccount = (BankAccount) data
                    .getSerializableExtra(AddBankAccountActivity.BANK_ACCOUNT_KEY);
            if (bankAccount != null) {
                Toast.makeText(getApplicationContext(),
                        R.string.main_new_bank_account_added_message,
                        Toast.LENGTH_SHORT)
                        .show();
                bankAccounts.add(bankAccount);
                notifyAdapter();
            }
        }
    }

    private void initComponents() {
        //initializare varaibile Java de tip componente vizuale cu elementele din xml
        fabAddBankAccount = findViewById(R.id.fab_main_add_bank_account);
        lvBankAccounts = findViewById(R.id.lv_main_bank_accounts);
        addBankAccountAdapter();
        fabAddBankAccount.setOnClickListener(addNewBankAccountEventListener());
    }

    private View.OnClickListener addNewBankAccountEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vezi Seminar 3 pentru transfer de parametri (pasul 1- deschidere formular)
                Intent intent = new Intent(getApplicationContext(), AddBankAccountActivity.class);
                startActivityForResult(intent, ADD_BANK_ACCOUNT_REQUEST_CODE);
            }
        };
    }

    private void addBankAccountAdapter() {
        //adaugare adapter pentru listview
//        ArrayAdapter<BankAccount> adapter = new ArrayAdapter<>(getApplicationContext(),
//                android.R.layout.simple_list_item_1, bankAccounts);
        BankAccountAdapter adapter = new BankAccountAdapter(getApplicationContext(),
                R.layout.lv_row_view, bankAccounts, getLayoutInflater());
        lvBankAccounts.setAdapter(adapter);
    }

    private void notifyAdapter() {
        //notificare adapter ca s-a adaugat un nou element in lista
        ArrayAdapter adapter = (ArrayAdapter) lvBankAccounts.getAdapter();
        adapter.notifyDataSetChanged();
    }
}