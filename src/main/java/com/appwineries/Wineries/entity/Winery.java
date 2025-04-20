package com.appwineries.Wineries.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Data
@Entity
@Table(name = "wineries")
public class Winery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private double latitude;
    private double longitude;
    private BigDecimal price;
    private Boolean food;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @Column(length = 1000)
    private String description;

    @Lob
    @Column(name = "photo", columnDefinition = "LONGTEXT")
    private String photo;

    @OneToMany(mappedBy = "winery", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "winery", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WineryWine> wines = new ArrayList<>();

    @OneToMany(mappedBy = "winery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> scheduleSlots = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "winery_extras", joinColumns = @JoinColumn(name = "winery_id"))
    @MapKeyColumn(name = "title")
    @Column(name = "value")
    private Map<String, String> extras = new HashMap<>();

    @Override
    public String toString() {
        return "Winery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", price=" + price +
                ", food=" + food +
                ", owner=" + owner +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", reservations=" + reservations +
                ", wines=" + wines +
                ", scheduleSlots=" + scheduleSlots +
                ", extras=" + extras +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getFood() {
        return food;
    }

    public User getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }


    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<WineryWine> getWines() {
        return wines;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setFood(Boolean food) {
        this.food = food;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setDescription(String description) {
        this.description = description;
    }

        public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setWines(List<WineryWine> wines) {
        this.wines = wines;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Schedule> getScheduleSlots() {
        return scheduleSlots;
    }

    public void setScheduleSlots(List<Schedule> scheduleSlots) {
        this.scheduleSlots = scheduleSlots;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }
}
