package com.example.dam_seminar_005.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Expense implements Parcelable {

    private Date date;
    private String category;
    private Double amount;
    private String description;

    public Expense(Date date, String category, Double amount, String description) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Date: " + new DateConverter().toString(date) +
                " Amount: " + amount +
                " Category: " + category +
                " Description: " + (description != null ? description : "-");
    }

    private Expense(Parcel source) {
        date = new DateConverter().fromString(source.readString());
        category = source.readString();
        amount = source.readDouble();
        description = source.readString();
    }

    public static Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel source) {
            return new Expense(source);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(new DateConverter().toString(date));
        dest.writeString(category);
        dest.writeDouble(amount);
        dest.writeString(description);
    }
}
