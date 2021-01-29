package com.example.a1088_vladescualexandrabianca;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String BOOKS_LIST = "books_list";

    public static final int ADD_REQUEST_CODE = 201;
    Button btn_add;
    Button btn_delete;
    Button btn_double;

    Spinner spn_list;

    List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.main_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        spn_list = findViewById(R.id.main_list);

        setAda();

        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = (Book) spn_list.getSelectedItem();
                books.remove(book);
                notifyAdapter();
            }
        });

        btn_double = findViewById(R.id.btn_double);
        btn_double.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = (Book) spn_list.getSelectedItem();
                books.add(book);
                notifyAdapter();
            }
        });

    }

    private void setAda() {
        ArrayAdapter<Book> adapter = new ArrayAdapter<Book>
                (getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, books);
        spn_list.setAdapter(adapter);
    }

    private void notifyAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) spn_list.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ADD_REQUEST_CODE) {
                Book book = (Book) data.getSerializableExtra(BookActivity.BOOK_KEY);
                books.add(book);
                setGraphFragment();
            }
        }
    }

    private void setGraphFragment() {
        GraphFragment fragment = new GraphFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BOOKS_LIST, (ArrayList<? extends Parcelable>) books);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();

        notifyAdapter();

    }
}
