package com.appwineries.Wineries.dto;

import java.math.BigDecimal;
import java.util.Map;

public class AllWineriesDTO {

    private Long id;
    private String name;
    private String location;
    private double latitude;
    private double longitude;
    private boolean food;
    private String photo;
    private String description;

    private Map<String, String> extras;

    private Map<String, String> offers;

    public AllWineriesDTO(Long id, String name, String location, double latitude, double longitude, Boolean food, String description, String photo) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.food = food != null ? food : false;
        this.photo = photo;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    public Map<String, String> getOffers() {
        return offers;
    }

    public void setOffers(Map<String, String> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "AllWineriesDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", food=" + food +
                ", photo='" + photo + '\'' +
                ", description='" + description + '\'' +
                ", extras=" + extras +
                ", offers=" + offers +
                '}';
    }
}
