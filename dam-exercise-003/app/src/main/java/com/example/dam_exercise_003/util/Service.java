package com.example.dam_exercise_003.util;

import java.io.Serializable;
import java.util.Date;

public class Service implements Serializable {

    public static String SERVICES = "services";
    public static String REG_NO = "regNo";
    public static String DEPARTMENT = "department";
    public static String COSTS = "costs";
    public static String DATE = "date";


    private int regNo;
    private String department;
    private double costs;
    private Date date;


    public Service(int regNo, String department, double costs, Date date) {
        this.regNo = regNo;
        this.department = department;
        this.costs = costs;
        this.date = date;
    }

    public int getRegNo() {
        return regNo;
    }

    public void setRegNo(int regNo) {
        this.regNo = regNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getCosts() {
        return costs;
    }

    public void setCosts(double costs) {
        this.costs = costs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
