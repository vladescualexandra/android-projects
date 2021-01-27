package com.example.dam_seminar_007;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.dam_seminar_007.async.AsyncTaskRunner;
import com.example.dam_seminar_007.async.Callback;
import com.example.dam_seminar_007.network.HttpManager;
import com.example.dam_seminar_007.util.BankAccount;
import com.example.dam_seminar_007.util.BankAccountAdapter;
import com.example.dam_seminar_007.util.BankAccountJSONParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_BANK_ACCOUNT_REQUEST_CODE = 210;
    private static final String URL_BANK_ACCOUNTS = "https://jsonkeeper.com/b/DHQ3";

    private FloatingActionButton fab_add;
    private ListView lv_main;
    private List<BankAccount> accounts = new ArrayList<>();
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        getBankAccountsFromUrl();
    }

    private void initComponents() {
        fab_add = findViewById(R.id.fab_main_add_bank_account);
        lv_main = findViewById(R.id.lv_main_bank_accounts);
        setAdapter();
        fab_add.setOnClickListener(addBankAccountEvent());
    }

    private View.OnClickListener addBankAccountEvent() {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddBankAccountActivity.class);
                startActivityForResult(intent, ADD_BANK_ACCOUNT_REQUEST_CODE);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_BANK_ACCOUNT_REQUEST_CODE
                && resultCode == RESULT_OK && data != null) {
            BankAccount account = (BankAccount) data.getSerializableExtra(AddBankAccountActivity.BANK_ACCOUNT_KEY);
            accounts.add(account);
            notifyAdapter();
        }
    }

    private void setAdapter() {
        BankAccountAdapter adapter = new BankAccountAdapter(getApplicationContext(),
                R.layout.main_row_item, accounts, getLayoutInflater());
        lv_main.setAdapter(adapter);
    }

    private void notifyAdapter() {
        BankAccountAdapter adapter = (BankAccountAdapter) lv_main.getAdapter();
        adapter.notifyDataSetChanged();
    }


    /******************* GET ACCOUNTS FROM JSON URL *******************/
    private void getBankAccountsFromUrl() {
        Callable<String> asyncOperation = new HttpManager(URL_BANK_ACCOUNTS);
        Callback<String> mainThreadOperation = mainThreadOperationGetBankAccountsFromUrl();
        asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);
    }

    private Callback<String> mainThreadOperationGetBankAccountsFromUrl() {
        return new Callback<String>() {
            @Override
            public void runResultOnUIThread(String result) {
                accounts.addAll(BankAccountJSONParser.fromJson(result));
                notifyAdapter();
            }
        };
    }
}