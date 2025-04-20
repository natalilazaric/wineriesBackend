package com.appwineries.Wineries.service.interfac;

import com.appwineries.Wineries.dto.LoginRequest;
import com.appwineries.Wineries.dto.Response;
import com.appwineries.Wineries.dto.UserDTO;
import com.appwineries.Wineries.entity.User;

public interface InterfaceUserService {

    Response register(User user);
    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response getUserReservationHistory(String userId);
    Response deleteUser(String userId);
    Response getUserById(String userId);
    Response getUserInfo(String email);
    Response updateUser(Long userId, UserDTO userDTO);
}
