package dam.application.graph;

import android.widget.DatePicker;
import android.widget.TimePicker;

public class Util {

    public static String fromDatePicker(DatePicker datePicker) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        return day + "/" + month + "/" + year;
    }

    public static String fromTimePicker(TimePicker timePicker) {
        int hh = 0;
        int mm = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hh = timePicker.getHour();
            mm = timePicker.getMinute();
        }

        return hh + ":" + mm;
    }
}
