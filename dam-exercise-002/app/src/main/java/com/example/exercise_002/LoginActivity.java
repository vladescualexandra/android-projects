package com.example.exercise_002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class LoginActivity extends AppCompatActivity {

    public static final String USER_INFO = "user_info";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String REMEMBER = "remember";
    public static final String NAME = "name";
    private Spinner spn_username;
    private EditText et_password;
    private CheckBox cb_remember;
    private Button btn_login;

    SharedPreferences prefs;
    String name;
    int username;
    String password;
    boolean remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();

        prefs = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        username = prefs.getInt(USERNAME, -1);
        password = prefs.getString(PASSWORD, null);
        remember = prefs.getBoolean(REMEMBER, false);

        if (remember) {
            if (username > -1) {
                spn_username.setSelection(username);
            }
            if (password != null) {
                et_password.setText(password);
            }
            cb_remember.setChecked(remember);
        }
    }

    private void initComponents() {
        spn_username = findViewById(R.id.login_username);
        et_password = findViewById(R.id.login_password);
        cb_remember = findViewById(R.id.login_remember);
        btn_login = findViewById(R.id.btn_login);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getApplicationContext(),
                        R.array.account_names,
                        R.layout.support_simple_spinner_dropdown_item);
        spn_username.setAdapter(adapter);
        btn_login.setOnClickListener(loginEvent());

    }

    private View.OnClickListener loginEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = spn_username.getSelectedItemPosition();
                name = spn_username.getSelectedItem().toString();
                password = et_password.getText().toString().trim();

                if (password.length() > 3) {
                    if (cb_remember.isChecked()) {
                        et_password.setError(null);
                        prefs = getSharedPreferences(USER_INFO, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(NAME, name);
                        editor.putInt(USERNAME, username);
                        editor.putString(PASSWORD, password);
                        editor.putBoolean(REMEMBER, cb_remember.isChecked());
                        editor.apply();
                        finish();
                    }
                } else {
                    et_password.setError(getString(R.string.invalid_passord));
                }
            }
        };
    }
}