package com.appwineries.Wineries.dto;

import com.appwineries.Wineries.entity.User;
import com.appwineries.Wineries.entity.Winery;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDTO {

    private Long id;
    private String date;
    private int numberOfGuests;
    private UserDTO user;
    private WineryDTO winery;
    private String offer;
    private BigDecimal totalPrice;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String state;

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", numberOfGuests=" + numberOfGuests +
                ", user=" + user +
                ", winery=" + winery +
                ", offer=" + offer +
                ", totalPrice=" + totalPrice +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public WineryDTO getWinery() {
        return winery;
    }

    public void setWinery(WineryDTO winery) {
        this.winery = winery;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
