package com.duantotnghiep.iwash.model;

public class Time {
    private String time;
    private boolean status;
    private boolean timeOut;

    public Time(String time, boolean status,boolean timeOut) {
        this.time = time;
        this.status = status;
        this.timeOut = timeOut;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isTimeOut() {
        return timeOut;
    }

    public void setTimeOut(boolean timeOut) {
        this.timeOut = timeOut;
    }
}
