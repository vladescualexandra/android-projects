package com.example.a1088_vladescualexandrabianca;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable, Parcelable {

    public static final String[] GENRES = {"sf", "thriller", "history"};

    private String name; // EditText
    private String genre; // Spinner
    private String date; // DatePicker
    private boolean finished; // CheckBox
    private int grade; // EditText

    public Book(String name, String genre, String date, boolean finished, int grade) {
        this.name = name;
        this.genre = genre;
        this.date = date;
        this.finished = finished;
        this.grade = grade;
    }

    protected Book(Parcel in) {
        name = in.readString();
        genre = in.readString();
        date = in.readString();
        finished = in.readByte() != 0;
        grade = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public static String fromDMY(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    public static List<Integer> toDMY(String value) {
        String[] pieces = value.split("/");
        List<Integer> numberPieces = new ArrayList<>();
        for (int i = 0; i < pieces.length; i++) {
            int n = Integer.parseInt(pieces[i]);
            numberPieces.add(n);
        }
        return numberPieces;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", date='" + date + '\'' +
                ", finished=" + finished +
                ", grade=" + grade +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(genre);
        dest.writeString(date);
        dest.writeByte((byte) (finished ? 1 : 0));
        dest.writeInt(grade);
    }
}
