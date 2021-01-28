package com.example.dam_exam_subject_004.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class CV implements Serializable {

    public static final String[] POSITIONS = {"Developer", "Tester", "Manager"};
    public static final String DEGREE_MASTER = "Master";
    public static final String DEGREE_BACHELOR = "Bachelor";

    private String name; // EditText
    private String position; // Spinner
    private int age; // SeekBar
    private String degree; // RadioGroup
    private boolean interview; // CheckBox

    public CV(String name, String position, int age, String degree, boolean interview) {
        this.name = name;
        this.position = position;
        this.age = age;
        this.degree = degree;
        this.interview = interview;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public boolean isInterview() {
        return interview;
    }

    public void setInterview(boolean interview) {
        this.interview = interview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CV cv = (CV) o;
        return age == cv.age &&
                interview == cv.interview &&
                name.equals(cv.name) &&
                position.equals(cv.position) &&
                degree.equals(cv.degree);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(name, position, age, degree, interview);
    }

    @Override
    public String toString() {
        return "CV{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", age=" + age +
                ", degree='" + degree + '\'' +
                ", interview=" + interview +
                '}';
    }
}
