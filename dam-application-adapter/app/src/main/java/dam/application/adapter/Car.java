package dam.application.adapter;

import java.io.Serializable;

public class Car implements Serializable {

    String model; // EditText
    int year; // Spinner
    float rating; // RatingBar
    boolean isSecondHand; // CheckBox

    public Car() {
    }

    public Car(String model, int year, float rating, boolean isSecondHand) {
        this.model = model;
        this.year = year;
        this.rating = rating;
        this.isSecondHand = isSecondHand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", isSecondHand=" + isSecondHand +
                '}';
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isSecondHand() {
        return isSecondHand;
    }

    public void setSecondHand(boolean secondHand) {
        isSecondHand = secondHand;
    }
}
