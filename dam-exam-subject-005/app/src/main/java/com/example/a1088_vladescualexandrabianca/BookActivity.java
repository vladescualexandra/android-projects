package com.example.a1088_vladescualexandrabianca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_KEY = "book_key";
    private String name; // EditText
    private String genre; // Spinner
    private String date; // DatePicker
    private boolean finished; // CheckBox
    private int grade; // EditText

    EditText et_name;
    Spinner spn_genre;
    DatePicker datePicker;
    CheckBox cb_finished;
    EditText et_grade;
    Button btn_add;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        intent = getIntent();

        et_name = findViewById(R.id.add_name);
        spn_genre = findViewById(R.id.add_genre);
        datePicker = findViewById(R.id.add_date);
        et_grade = findViewById(R.id.add_grade);
        cb_finished = findViewById(R.id.add_finished);
        btn_add = findViewById(R.id.add_save);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>
                (getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Book.GENRES);
        spn_genre.setAdapter(adapter);

        btn_add.setOnClickListener(saveEvent());

    }

    private View.OnClickListener saveEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validare()) {
                    name = et_name.getText().toString().trim();
                    genre = spn_genre.getSelectedItem().toString();
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth();
                    int day = datePicker.getDayOfMonth();
                    date = Book.fromDMY(day, month, year);
                    grade = Integer.parseInt(et_grade.getText().toString());
                    finished = cb_finished.isChecked();


                    Book book = new Book(name, genre, date, finished, grade);

                    intent.putExtra(BOOK_KEY, (Serializable) book);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            }
        };
    }

    private boolean validare() {
        if (et_name.getText().length() < 3) {
            et_name.setError(getString(R.string.a));
            return false;
        } else {
            et_name.setError(null);

            if (et_grade.getText().toString() != null) {
                grade = Integer.parseInt(et_grade.getText().toString());

                if (grade > 0 && grade <= 10) {
                    return true;
                } else {
                    et_grade.setError(getString(R.string.invalid));
                }

            } else {
                et_grade.setError(null);
            }

            return true;
        }


    }
}