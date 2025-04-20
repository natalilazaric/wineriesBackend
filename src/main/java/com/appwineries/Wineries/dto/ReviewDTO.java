package com.appwineries.Wineries.dto;

import com.appwineries.Wineries.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDTO {

    private Long id;
    private String text;
    private Long userId;
    private Long wineryId;
    private Long reservationId;
    private LocalDateTime createdAt;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String text, LocalDateTime createdAt, Long wineryId, Long userId, Long reservationId) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.wineryId = wineryId;
        this.userId = userId;
        this.reservationId = reservationId;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", userId=" + userId +
                ", wineryId=" + wineryId +
                ", reservationId=" + reservationId +
                ", createdAt=" + createdAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWineryId() {
        return wineryId;
    }

    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
