package dam.application.json;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_add;
    ListView lv_main;

    List<Book> bookList = new ArrayList<>();
    final int REQUEST_CODE_ADD = 201;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.main_add);
        btn_add.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddBookActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });
        lv_main = findViewById(R.id.main_lv);
        setAdapter();
    }

    private void setAdapter() {
        ArrayAdapter<Book> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        bookList);
        lv_main.setAdapter(adapter);
    }

    private void notifyAdapter() {
        ArrayAdapter<Book> adapter = (ArrayAdapter) lv_main.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Book book = (Book) data.getSerializableExtra(AddBookActivity.EXTRA);
            if (requestCode == REQUEST_CODE_ADD) {
                bookList.add(book);
                notifyAdapter();
            }
        }
    }


    /* OLD MENU */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_old_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        String url = "https://jsonkeeper.com/b/BVD6";
        if (item.getItemId() == R.id.old_menu_incarca) {
            incarcaDinJSON(url);
        } else if (item.getItemId() == R.id.old_menu_salveaza) {
            descarcaFisier(url);
        }
        return true;
    }

    private void incarcaDinJSON(String url) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                HttpManager httpManager = new HttpManager(url);
                try {
                    String result = httpManager.call();
                    Log.e("test", result);
                    if (result != null) {

                        JSONObject jsonObject = new JSONObject(result);
                        String title = jsonObject.getString("title");
                        String link = jsonObject.getString("link");
                        String genre = jsonObject.getString("genre");
                        int price = jsonObject.getInt("price");
                        int rating = jsonObject.getInt("rating");

                        Book book = new Book(title, link, genre, price, rating);
                        bookList.add(book);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyAdapter();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }


    private void descarcaFisier(String url) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
//                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                    request.allowScanningByMediaScanner();
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
//                    DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                    manager.enqueue(request);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}