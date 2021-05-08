package com.duantotnghiep.iwash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Schedule {
    @SerializedName("services")
    @Expose
    private List<Service> services;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vehicleStatus")
    @Expose
    private boolean vehicleStatus;
    @SerializedName("idStaffConfirm")
    @Expose
    private String idStaffConfirm;
    @SerializedName("timeConfirm")
    @Expose
    private String timeConfirm;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("idUser")
    @Expose
    private User user;
    @SerializedName("timeBook")
    @Expose
    private String timeBook;
    @SerializedName("vehicle")
    @Expose
    private Vehicle vehicle;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdStaffConfirm() {
        return idStaffConfirm;
    }

    public void setIdStaffConfirm(String idStaffConfirm) {
        this.idStaffConfirm = idStaffConfirm;
    }

    public String getTimeConfirm() {
        return timeConfirm;
    }

    public void setTimeConfirm(String timeConfirm) {
        this.timeConfirm = timeConfirm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimeBook() {
        return timeBook;
    }

    public void setTimeBook(String timeBook) {
        this.timeBook = timeBook;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(boolean vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
