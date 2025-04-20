package com.appwineries.Wineries.controller;

import com.appwineries.Wineries.dto.Response;
import com.appwineries.Wineries.dto.UserDTO;
import com.appwineries.Wineries.service.interfac.InterfaceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private InterfaceUserService interfaceUserService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAllUsers(){
        Response response = interfaceUserService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable("userId") String userId){
        Response response = interfaceUserService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Response> deleteUser(@PathVariable("userId") String userId){
        Response response = interfaceUserService.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-profile-info")
    public ResponseEntity<Response> getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Response response = interfaceUserService.getUserInfo(userEmail);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-user-reservations/{userId}")
    public ResponseEntity<Response> getUserReservationHistory(@PathVariable("userId") String userId){
        Response response = interfaceUserService.getUserReservationHistory(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<Response> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        Response response = interfaceUserService.updateUser(userId, userDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
