package com.duantotnghiep.iwash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleBody {
    @SerializedName("timeBook")
    @Expose
    private String timeBook;
    @SerializedName("vehicle")
    @Expose
    private String vehicle;
    @SerializedName("service")
    @Expose
    private List<Service> services;
    @SerializedName("note")
    @Expose
    private String note;

    public String getTimeBook() {
        return timeBook;
    }

    public void setTimeBook(String timeBook) {
        this.timeBook = timeBook;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
