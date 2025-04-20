package com.appwineries.Wineries.controller;

import com.appwineries.Wineries.dto.LoginRequest;
import com.appwineries.Wineries.dto.Response;
import com.appwineries.Wineries.entity.User;
import com.appwineries.Wineries.service.interfac.InterfaceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private InterfaceUserService interfaceUserService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user){
        Response response = interfaceUserService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        Response response = interfaceUserService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
