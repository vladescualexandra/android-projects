package eu.ase.ro.seminar10.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.room.TypeConverter;

public class DateConverter {
    private static final String FORMAT_DATE = "dd/MM/yyyy";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE, Locale.US);

    @TypeConverter
    public static Date fromString(String value) {
        try {
            //metoda parsa este folosita pentru conversia String to Date
            return formatter.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(Date value) {
        if (value == null) {
            return null;
        }
        //metoda format este utilizata pentru conversia Date to String
        return formatter.format(value);
    }
}
