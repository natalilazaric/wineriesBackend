package com.appwineries.Wineries.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;
    private String token;
    private String role;
    private String expirationTime;

    private UserDTO user;
    private WineryDTO winery;
    private ReservationDTO reservation;
    private WineryWineDTO wineryWine;
    private AllWineriesDTO allWineriesDTO;
    private List<UserDTO> userList;
    private List<AllWineriesDTO> wineryList;
    private List<ReservationDTO> reservationList;
    private List<WineryWineDTO> wineryWineList;

    private List<String> wineNames;

    private Object schedule;
    private List<ScheduleDTO> scheduleList;

    private ReviewDTO review;
    private List<ReviewDTO> reviewDTOList;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public ReservationDTO getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDTO reservation) {
        this.reservation = reservation;
    }

    public List<UserDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }

    public List<AllWineriesDTO> getWineryList() {
        return wineryList;
    }

    public void setWineryList(List<AllWineriesDTO> wineryList) {
        this.wineryList = wineryList;
    }

    public List<ReservationDTO> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<ReservationDTO> reservationList) {
        this.reservationList = reservationList;
    }

    public WineryWineDTO getWineryWine() {
        return wineryWine;
    }

    public void setWineryWine(WineryWineDTO wineryWine) {
        this.wineryWine = wineryWine;
    }

    public List<WineryWineDTO> getWineryWineList() {
        return wineryWineList;
    }

    public void setWineryWineList(List<WineryWineDTO> wineryWineList) {
        this.wineryWineList = wineryWineList;
    }

    public List<String> getWineNames() {
        return wineNames;
    }

    public void setWineNames(List<String> wineNames) {
        this.wineNames = wineNames;
    }

    public AllWineriesDTO getAllWineriesDTO() {
        return allWineriesDTO;
    }

    public void setAllWineriesDTO(AllWineriesDTO allWineriesDTO) {
        this.allWineriesDTO = allWineriesDTO;
    }

    public Object getSchedule() {
        return schedule;
    }

    public void setSchedule(Object schedule) {
        this.schedule = schedule;
    }

    public List<ScheduleDTO> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<ScheduleDTO> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public ReviewDTO getReview() {
        return review;
    }

    public void setReview(ReviewDTO review) {
        this.review = review;
    }

    public List<ReviewDTO> getReviewDTOList() {
        return reviewDTOList;
    }

    public void setReviewDTOList(List<ReviewDTO> reviewDTOList) {
        this.reviewDTOList = reviewDTOList;
    }
}
