package com.duantotnghiep.iwash.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponse {
    @SerializedName("success")
    public boolean success;
    @SerializedName("token")
    public String token;
    @SerializedName("message")
    public String message;
    @SerializedName("user")
    public User user;
    @SerializedName("users")
    public List<User> users;
    @SerializedName("vehicles")
    public List<Vehicle> vehicles;
    @SerializedName("services")
    public List<Service> services;
    @SerializedName("news")
    public List<News> newsList;
    @SerializedName("schedules")
    public List<Schedule> schedules;
    @SerializedName("schedule")
    public Schedule schedule;
    @SerializedName("notify")
    public List<Notification> notifications;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
