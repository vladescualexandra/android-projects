package com.example.dam_exercise_004.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "rides")
public class BusRide implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name="destination")
    private String destination;

    @ColumnInfo(name="price")
    private int price;

    public BusRide(long id, String destination, int price) {
        this.id = id;
        this.destination = destination;
        this.price = price;
    }

    @Ignore
    public BusRide(String destination, int price) {
        this.destination = destination;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return destination + " - " + price;
    }
}
