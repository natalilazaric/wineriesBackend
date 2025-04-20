package com.appwineries.Wineries.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleDTO {

    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private int maxReservations;
    private int maxGuests;

    public ScheduleDTO(String dayOfWeek, LocalTime startTime, LocalTime endTime, int maxGuests, int maxReservations) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime.toString();
        this.endTime = endTime.toString();
        this.maxGuests = maxGuests;
        this.maxReservations = maxReservations;
    }

    @Override
    public String toString() {
        return "ScheduleDTO{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", maxReservations=" + maxReservations +
                ", maxGuests=" + maxGuests +
                '}';
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

    public int getMaxReservations() {
        return maxReservations;
    }

    public void setMaxReservations(int maxReservations) {
        this.maxReservations = maxReservations;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }
}
