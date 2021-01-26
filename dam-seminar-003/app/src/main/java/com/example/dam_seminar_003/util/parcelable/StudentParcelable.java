package com.example.dam_seminar_003.util.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.dam_seminar_003.util.DateConverter;
import com.example.dam_seminar_003.util.StudyType;

import java.util.Date;

public class StudentParcelable implements Parcelable {
    private String name;
    private int age;
    private Date enrollment;
    private StudyType studyType;
    private String faculty;


    public StudentParcelable(String name,
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

    /*
    * Creatorul este public deoarece acesta trebuie să fie invocat de pachetul
    * android care se află în exteriorul clasei noastre.
    * Este static deoarece dorim să obținem o instanță Java pe baza fișierului
    * parcel, ceea ce înseamnă că nu poate depinde de vreo instanță, ci de clasă.
    */

    public static Creator<StudentParcelable> CREATOR = new Creator<StudentParcelable>() {
        @Override
        public StudentParcelable createFromParcel(Parcel source) {
            return new StudentParcelable(source);
        }

        @Override
        public StudentParcelable[] newArray(int size) {
            return new StudentParcelable[size];
        }
    };

    protected StudentParcelable(Parcel source) {
        // Ordinea de scriere în fișier trebuie respectată și la citire
        name = source.readString();
        age = source.readInt();
        enrollment = new DateConverter().fromString(source.readString());
        studyType = StudyType.FULL_TIME.name().equals(source.readString()) ?
                StudyType.FULL_TIME : StudyType.DISTANCE;
        faculty = source.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Ordinea de scriere în fișier trebuie respectată și la citire
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(new DateConverter().toString(enrollment));
        dest.writeString(studyType.name());
        dest.writeString(faculty);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
