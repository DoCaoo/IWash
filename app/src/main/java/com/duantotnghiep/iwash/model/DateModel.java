package com.duantotnghiep.iwash.model;

public class DateModel {
    String Date;
    String day;
    String thisDay;

    public DateModel(String date, String day, String thisDay) {
        this.Date = date;
        this.day = day;
        this.thisDay = thisDay;
    }

    public String getThisDay() {
        return thisDay;
    }

    public void setThisDay(String thisDay) {
        this.thisDay = thisDay;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
