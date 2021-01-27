package com.example.dam_seminar_010.util;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final String FORMAT_DATE = "dd/MM/yyyy";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE, Locale.US);

    @TypeConverter
    public static Date fromString(String value) {
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(Date value) {
        if (value == null) {
            return null;
        } else {
            return formatter.format(value);
        }
    }
}
