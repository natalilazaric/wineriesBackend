package com.appwineries.Wineries.utils;

import com.appwineries.Wineries.dto.*;
import com.appwineries.Wineries.entity.*;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomAlphanumeric(int length){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<length; i++){
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUserEntityToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static ReservationDTO mapReservationEntityToReservationDTO(Reservation reservation){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setNumberOfGuests(reservation.getNumberOfGuests());
        reservationDTO.setTotalPrice(reservation.getTotalPrice());
        reservationDTO.setDayOfWeek(reservation.getDayOfWeek());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setEndTime(reservation.getEndTime());
        reservationDTO.setState(reservation.getState());

        reservationDTO.setUser(Utils.mapUserEntityToUserDTO(reservation.getUser()));

        if(reservationDTO.getWinery() != null){
            reservationDTO.setWinery(Utils.mapWineryEntityToWineryDTO(reservation.getWinery()));
        }
        return reservationDTO;
    }

    public static WineryDTO mapWineryEntityToWineryDTO(Winery winery){
        WineryDTO wineryDTO = new WineryDTO();
        wineryDTO.setId(winery.getId());
        wineryDTO.setName(winery.getName());
        wineryDTO.setLocation(winery.getLocation());
        wineryDTO.setLatitude(winery.getLatitude());
        wineryDTO.setLongitude(winery.getLongitude());
        wineryDTO.setPrice(winery.getPrice());
        wineryDTO.setFood(winery.getFood());
        wineryDTO.setPhoto(winery.getPhoto());
        wineryDTO.setDescription(winery.getDescription());
        wineryDTO.setExtras(winery.getExtras());
        wineryDTO.setOffers(winery.getOffers());

        if(winery.getOwner() != null){
            wineryDTO.setOwner(Utils.mapUserEntityToUserDTO(winery.getOwner()));
        }

        return wineryDTO;
    }

    public static WineryWineDTO mapWineryWineEntityToWineryWineDTO(WineryWine wineryWine){
        WineryWineDTO wineryWineDTO = new WineryWineDTO();
        wineryWineDTO.setId(wineryWine.getId());
        wineryWineDTO.setWinery(wineryWine.getWinery());
        wineryWineDTO.setWineName(wineryWine.getWineName());

        return wineryWineDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusUserReservationAndWinery(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());

        if (!user.getReservations().isEmpty()){
            userDTO.setReservations(user.getReservations().stream().map(reservation -> mapReservationEntityToReservationDTOPlusReservedWinery(reservation, false)).collect(Collectors.toList()));
        }
        return userDTO;
    }

    public static ReservationDTO mapReservationEntityToReservationDTOPlusReservedWinery(Reservation reservation, boolean mapUser){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setNumberOfGuests(reservation.getNumberOfGuests());
        reservationDTO.setTotalPrice(reservation.getTotalPrice());
        reservationDTO.setState(reservation.getState());
        reservationDTO.setDayOfWeek(reservation.getDayOfWeek());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setEndTime(reservation.getEndTime());

        if(reservationDTO.getWinery() != null){
            reservationDTO.setWinery(Utils.mapWineryEntityToWineryDTO(reservation.getWinery()));
        }

        if(mapUser){
            reservationDTO.setUser(Utils.mapUserEntityToUserDTO(reservation.getUser()));
        }

        if(reservation.getWinery() != null){
            WineryDTO wineryDTO = new WineryDTO();
            wineryDTO.setId(reservation.getWinery().getId());
            wineryDTO.setName(reservation.getWinery().getName());
            wineryDTO.setLocation(reservation.getWinery().getLocation());
            wineryDTO.setPrice(reservation.getWinery().getPrice());
            wineryDTO.setFood(reservation.getWinery().getFood());
            wineryDTO.setPhoto(reservation.getWinery().getPhoto());
            wineryDTO.setDescription(reservation.getWinery().getDescription());
            wineryDTO.setExtras(reservation.getWinery().getExtras());
            wineryDTO.setOffers(reservation.getWinery().getOffers());
            reservationDTO.setWinery(wineryDTO);
        }

        return reservationDTO;
    }

    private static ReviewDTO mapReviewEntityToReviewDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setText(review.getText());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUserId(review.getUser().getId());
        dto.setWineryId(review.getWinery().getId());
        dto.setReservationId(review.getReservation() != null ? review.getReservation().getId() : null);
        return dto;
    }


    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList){
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());

    }


    public static List<ReservationDTO> mapReservationListEntityToReservationListDTO(List<Reservation> reservationList){
        return reservationList.stream().map(Utils::mapReservationEntityToReservationDTO).collect(Collectors.toList());

    }

    public static List<ReviewDTO> mapReviewListEntityToReviewListDTO(List<Review> reviewList){
        return reviewList.stream().map(Utils::mapReviewEntityToReviewDTO).collect(Collectors.toList());

    }

}













