package com.example.dam_exam_subject_003.util;

import android.widget.CheckBox;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "websites")
public class Website implements Serializable {

    public static final String NAME_1 = "A-website 1";
    public static final String NAME_2 = "B-website 2";
    public static final String NAME_3 = "C-website 3";

    public static final String[] WEBSITES = {NAME_1, NAME_2, NAME_3};

    public static final String TYPE_ENTERTAINMENT = "Entertainment";
    public static final String TYPE_WORK = "Work";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;
    @ColumnInfo(name="name")
    private String name; // Spinner
    @ColumnInfo(name="date")
    private String date; // DatePicker
    @ColumnInfo(name="time")
    private String time; // TimePicker;
    @ColumnInfo(name="type")
    private String type; // RadioGroup -> Entertainment/Work
    @ColumnInfo(name="span")
    private int span; // SeekBar -> as in Attention Span
    @ColumnInfo(name="incognito")
    private boolean incognito; // CheckBox

    @Ignore
    public Website(String name, String date, String time, String type, int span, boolean incognito) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.type = type;
        this.span = span;
        this.incognito = incognito;
    }

    public Website(long id, String name, String date, String time, String type, int span, boolean incognito) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.type = type;
        this.span = span;
        this.incognito = incognito;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public boolean isIncognito() {
        return incognito;
    }

    public void setIncognito(boolean incognito) {
        this.incognito = incognito;
    }

    @Override
    public String toString() {
        return "Website{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", span=" + span +
                ", incognito=" + incognito +
                '}';
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

    public static String fromHM(int hh, int mm) {
        return hh + ":" + mm;
    }

    public static List<Integer> toHM(String value) {
        String[] pieces = value.split(":");
        List<Integer> numberPieces = new ArrayList<>();
        for (int i = 0; i < pieces.length; i++) {
            int n = Integer.parseInt(pieces[i]);
            numberPieces.add(n);
        }
        return numberPieces;
    }

}
