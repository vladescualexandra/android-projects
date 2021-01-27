package com.example.dam_exercise_003;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dam_exercise_003.async.AsyncTaskRunner;
import com.example.dam_exercise_003.async.Callback;
import com.example.dam_exercise_003.network.HttpManager;
import com.example.dam_exercise_003.util.Service;
import com.example.dam_exercise_003.util.ServiceAdapter;
import com.example.dam_exercise_003.util.ServiceJSONParser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    private static final String URL_ADDRESS = "https://jsonkeeper.com/b/1F06";
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    private TextView welcome_message;
    private ListView main_lv;
    private List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        setAdapter();
        getServicesFromHttp();

        getPrefs();
    }

    private void getPrefs() {
        SharedPreferences prefs = getSharedPreferences(ProfileActivity.USER_KEY, MODE_PRIVATE);
        String name = prefs.getString(ProfileActivity.NAME, null);
        String position = prefs.getString(ProfileActivity.POSITION, null);

        if (name != null) {
            welcome_message.setText(getString(R.string.welcome_message_format, name));
        }
    }

    private void initComponents() {
        welcome_message = findViewById(R.id.main_welcome_message);
        main_lv = findViewById(R.id.main_lv);
        services = new ArrayList<>();
    }

    private void setAdapter() {
        ServiceAdapter adapter = new ServiceAdapter(getApplicationContext(),
                R.layout.main_row_item,
                services,
                getLayoutInflater());
        main_lv.setAdapter(adapter);
    }

    private void notifyAdapter() {
        ServiceAdapter adapter = (ServiceAdapter) main_lv.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void getServicesFromHttp() {
        Callable<String> asyncOperation = new HttpManager(URL_ADDRESS);
        Callback<String> mainThreadOperation = receiveServices();
        asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);
    }

    private Callback<String> receiveServices() {
        return new Callback<String>() {
            @Override
            public void runResultOnUIThread(String result) {
                services.addAll(ServiceJSONParser.fromJson(result));
                notifyAdapter();
            }
        };
    }

    /************************ CLASSIC MENU ************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_old_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.old_menu_profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }
        return true;
    }
}