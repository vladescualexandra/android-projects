package dam.application.graph;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Wedding implements Serializable, Parcelable {

    private String name; // EditText
    private String date; // DatePicker
    private String time; // TimePicker
    private int guests; // SeekBar
    private String hall; // Spinner

    public Wedding() {
    }

    public Wedding(String name, String date, String time, int guests, String hall) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.guests = guests;
        this.hall = hall;
    }

    protected Wedding(Parcel in) {
        name = in.readString();
        date = in.readString();
        time = in.readString();
        guests = in.readInt();
        hall = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(guests);
        dest.writeString(hall);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Wedding> CREATOR = new Creator<Wedding>() {
        @Override
        public Wedding createFromParcel(Parcel in) {
            return new Wedding(in);
        }

        @Override
        public Wedding[] newArray(int size) {
            return new Wedding[size];
        }
    };

    @Override
    public String toString() {
        return "Wedding{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", guests=" + guests +
                ", hall='" + hall + '\'' +
                '}';
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

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }
}
