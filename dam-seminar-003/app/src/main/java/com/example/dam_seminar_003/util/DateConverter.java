package com.example.dam_seminar_003.util;

import android.provider.ContactsContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final String FORMAT_DATE = "dd/MM/yyyy";
    private final SimpleDateFormat formatter;

    public DateConverter() {
        formatter = new SimpleDateFormat(FORMAT_DATE, Locale.US);
    }

    public Date fromString(String value) {
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    public String toString(Date value) {
        if (value == null) {
            return null;
        } else {
            return formatter.format(value);
        }
    }

}
