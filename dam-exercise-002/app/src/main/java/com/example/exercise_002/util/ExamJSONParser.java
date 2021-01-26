package com.example.exercise_002.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamJSONParser {

    public static final String EXAMS = "exams";
    public static final String COURSE = "course";
    public static final String DATE = "date";
    public static final String CLASSROOM = "classroom";
    public static final String SUPERVISOR = "supervisor";

    public static List<Exam> fromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray exams = jsonObject.getJSONArray(EXAMS);
            return readExams(exams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<Exam> readExams(JSONArray array) throws JSONException {
        List<Exam> exams = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Exam exam = buildExam(array.getJSONObject(i));
            exams.add(exam);
        }
        return exams;
    }

    private static Exam buildExam(JSONObject object) throws JSONException {
        String course = object.getString(COURSE);
        String date = object.getString(DATE);
        Date parsedDate = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            parsedDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String classroom = object.getString(CLASSROOM);
        String supervisor = object.getString(SUPERVISOR);

        return new Exam(course, parsedDate, classroom, supervisor);
    }
}
