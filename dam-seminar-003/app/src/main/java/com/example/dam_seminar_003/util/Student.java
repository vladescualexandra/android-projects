package com.example.dam_seminar_003.util;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    private String name;
    private int age;
    private Date enrollment;
    private StudyType studyType;
    private String faculty;

    public Student(String name,
                   int age,
                   Date enrollment,
                   StudyType studyType,
                   String faculty) {
        this.name = name;
        this.age = age;
        this.enrollment = enrollment;
        this.studyType = studyType;
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Date enrollment) {
        this.enrollment = enrollment;
    }

    public StudyType getStudyType() {
        return studyType;
    }

    public void setStudyType(StudyType studyType) {
        this.studyType = studyType;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n"
        + "Age: " + age + "\n"
        + "Enrolled: " + new DateConverter().toString(enrollment) + "\n"
        + "Faculty: " + faculty  + "/ " + studyType;
    }
}
