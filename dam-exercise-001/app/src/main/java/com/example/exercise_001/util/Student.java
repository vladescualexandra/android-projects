package com.example.exercise_001.util;

import java.io.Serializable;

public class Student implements Serializable {

    public static final String FACULTATE_CSIE = "CSIE";
    public static final String FACULTATE_MRK = "MRK";
    public static final String FACULTATE_CIG = "CIG";

    private String nume;
    private int grupa;
    private String serie;
    private int an;
    private String facultate;

    public Student(String nume, int grupa, String serie, int an, String facultate) {
        this.nume = nume;
        this.grupa = grupa;
        this.serie = serie;
        this.an = an;
        this.facultate = facultate;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public String getFacultate() {
        return facultate;
    }

    public void setFacultate(String facultate) {
        this.facultate = facultate;
    }
}
