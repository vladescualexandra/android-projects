package com.example.dam_exam_subject_002.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Marriage implements Serializable, Parcelable {

    public static final String HALL_1 = "hall 1";
    public static final String HALL_2 = "hall 2";
    public static final String HALL_3 = "hall 3";

    public static final String[] HALLS = {HALL_1, HALL_2, HALL_3};

    private String name; // EditText
    private String date; // DatePicker
    private String time; // TimePicker
    private String hall; // Spinner
    private int guests;// SeekBar
    private MType type; // RadioButton

    public Marriage() {

    }

    public Marriage(String name, String date,
                    String time, String hall,
                    int guests, MType type) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.hall = hall;
        this.guests = guests;
        this.type = type;
    }

    protected Marriage(Parcel in) {
        name = in.readString();
        date = in.readString();
        time = in.readString();
        hall = in.readString();
        guests = in.readInt();
        String t =  in.readString();
        if (t.equals(MType.FF.name())) {
            type = MType.FF;
        } else if (t.equals(MType.MM.name())) {
            type = MType.MM;
        } else {
            type = MType.FM;
        }
    }

    public static final Creator<Marriage> CREATOR = new Creator<Marriage>() {
        @Override
        public Marriage createFromParcel(Parcel in) {
            return new Marriage(in);
        }

        @Override
        public Marriage[] newArray(int size) {
            return new Marriage[size];
        }
    };

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

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public MType getType() {
        return type;
    }

    public void setType(MType type) {
        this.type = type;
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


    @Override
    public String toString() {
        return "Marriage{" +
                "name='" + name + '\n' +
                ", date='" + date + '\n' +
                ", time='" + time + '\n' +
                ", hall='" + hall + '\n' +
                ", guests=" + guests + "\n" +
                ", type=" + type+
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(hall);
        dest.writeInt(guests);
        dest.writeString(type.name());
    }
}
