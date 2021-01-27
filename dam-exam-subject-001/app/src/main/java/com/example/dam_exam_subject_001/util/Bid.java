package com.example.dam_exam_subject_001.util;

import android.widget.DatePicker;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Bid implements Serializable, Comparable<Bid> {


    private String name; // EditText
    private State state; // RadioGroup - RadioButton
    private String object; // Spinner
    private Date date; // DatePicker
    private String time; // TimePicker
    private boolean registered;// CheckBox
    private int bid; // amount - SeekBar

    public Bid(String name, State state,
               String object, Date date,
               String time, boolean registered,
               int bid) {
        this.name = name;
        this.state = state;
        this.object = object;
        this.date = date;
        this.time = time;
        this.registered = registered;
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\n"
                + "State: " + this.state + "\n"
                + "Object: " + this.object + "\n"
                + "Date: " + this.date + "\n"
                + "Time: " + this.time + "\n"
                + "Registered: " + this.registered + "\n"
                + "Bid: " + this.bid;
    }

    public static Date fromDatePicker(DatePicker datePicker) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        String strDate = day + "/" + month + "/" + year;
        return fromString(strDate);
    }

    public static Date fromString(String strDate) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }

    public static String convertTime(int hh, int mm) {
        return hh + ":" + mm;
    }

    @Override
    public int compareTo(Bid o) {
        if (this.bid < o.getBid()) return  -1;
        if (this.bid == o.getBid()) return 0;
        return 1;
    }
}
