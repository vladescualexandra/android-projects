package com.example.exercise_002.util;

import java.io.Serializable;
import java.util.Date;

public class Exam implements Serializable {
    private String course;
    private Date date;
    private String classroom;
    private String supervisor;

    public Exam(String course, Date date, String classroom, String supervisor) {
        this.course = course;
        this.date = date;
        this.classroom = classroom;
        this.supervisor = supervisor;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
}
