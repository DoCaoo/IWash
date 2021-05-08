
package com.duantotnghiep.iwash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehicle {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("idUser")
    @Expose
    private User user;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("license")
    @Expose
    private String license;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getIdUser() {
        return user;
    }

    public void setIdUser(User idUser) {
        this.user = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }


    public Vehicle(String name, String type, String license, String color, String brand) {
        this.name = name;
        this.type = type;
        this.license = license;
        this.color = color;
        this.brand = brand;
    }
}
