package com.appwineries.Wineries.service.impl;

import com.appwineries.Wineries.dto.LoginRequest;
import com.appwineries.Wineries.dto.Response;
import com.appwineries.Wineries.dto.UserDTO;
import com.appwineries.Wineries.entity.User;
import com.appwineries.Wineries.exception.OurException;
import com.appwineries.Wineries.repo.UserRepository;
import com.appwineries.Wineries.service.interfac.InterfaceUserService;
import com.appwineries.Wineries.utils.JWTUtils;
import com.appwineries.Wineries.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements InterfaceUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Response register(User user) {
        Response response = new Response();
        try{
            if(userRepository.existsByEmail(user.getEmail())){
                throw new OurException(user.getEmail() + " already exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDto = Utils.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(userDto);

        }catch (OurException ex){
            response.setStatusCode(400);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error occurred during user registration " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new OurException("User not found"));

            var token =jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("Successful");

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error occurred during user login " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try{
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUserList(userDTOList);

        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting all users " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserReservationHistory(String userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserReservationAndWinery(user);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUser(userDTO);

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting user reservation history " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();
        try{
            userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("Successful");

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting all users " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUser(userDTO);

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting all users " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserInfo(String email) {
        Response response = new Response();
        try{
            User user = userRepository.findByEmail(email).orElseThrow(()->new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUser(userDTO);

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting all users " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response updateUser(Long userId, UserDTO userDTO) {
        Response response = new Response();
        try{
            User user = userRepository.findById(userId).orElseThrow(()->new OurException("User not found"));
            user.setName(userDTO.getName());
            user.setLastname(userDTO.getLastname());
            userRepository.save(user);
            response.setStatusCode(200);
            response.setMessage("Successful");

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting all users " + ex.getMessage());
        }
        return response;
    }
}
