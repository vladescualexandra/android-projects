package dam.application.json;

import java.io.Serializable;

public class Book implements Serializable {

    String title; // EditText
    String link; // Spinner
    String genre; // RadioGroup
    int price; // SeekBar
    int rating; // RatingBar

    public Book() {
    }

    public Book(String title, String link, String genre, int price, int rating) {
        this.title = title;
        this.link = link;
        this.genre = genre;
        this.price = price;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + link + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
