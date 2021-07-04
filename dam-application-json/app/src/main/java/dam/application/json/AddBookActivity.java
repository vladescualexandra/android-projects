package dam.application.json;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;

public class AddBookActivity extends AppCompatActivity {

    public static String EXTRA = "extra";
    Intent intent;
    Book book = new Book();

    EditText et_title;
    Spinner spn_author;
    RadioGroup rg_genre;
    SeekBar sb_price;
    RatingBar rb_rating;
    Button btn_save;

    String link1 = "Author1";
    String link2 = "Author2";
    String[] links = {link1, link2};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        initComponents();
    }

    private void initComponents() {
        intent = getIntent();
        et_title = findViewById(R.id.add_title);
        spn_author = findViewById(R.id.add_author);
        setSpinnerAdapter();
        rg_genre = findViewById(R.id.add_genre);
        sb_price = findViewById(R.id.add_price);
        rb_rating = findViewById(R.id.add_rating);
        btn_save = findViewById(R.id.add_save);

        btn_save.setOnClickListener(v -> {
            if (validate()) {
                build();
                intent.putExtra(EXTRA, book);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private boolean validate() {
        if (et_title.getText() == null
                || et_title.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private void build() {
        String title = et_title.getText().toString();
        book.setTitle(title);

        String link = spn_author.getSelectedItem().toString();
        book.setLink(link);

        if (rg_genre.getCheckedRadioButtonId() == R.id.add_genre_1) {
            book.setGenre("genre1");
        } else {
            book.setGenre("genre2");
        }

        int price = sb_price.getProgress();
        book.setPrice(price);

        int rating = (int) rb_rating.getRating();
        book.setRating(rating);
    }


    private void setSpinnerAdapter() {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        links);
        spn_author.setAdapter(adapter);
    }
}