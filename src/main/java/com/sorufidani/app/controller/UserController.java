package com.sorufidani.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sorufidani.app.dao.UserDaoImpl;
import com.sorufidani.app.model.CreateUserRequest;
import com.sorufidani.app.model.User;
import com.sorufidani.app.service.JWTService;
import com.sorufidani.app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class UserController {
    private final JWTService jwtService;
    private final UserService userService;

    @Autowired
    public UserController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping(value = "/create-user", produces = "application/json")
    public String createUser(@RequestBody CreateUserRequest createUserRequest) throws JsonProcessingException {
        return userService.createUser(createUserRequest);
    }

    @GetMapping(value = "/token", produces = "application/json")
    public String generateJwtToken(@RequestParam String mail, @RequestParam String password, @RequestParam String type, HttpServletRequest request) throws SQLException, NoSuchAlgorithmException {
        return jwtService.generateJwtToken(mail, password, type, request);

    }

    @PostMapping(value = "/forgot-password", produces = "application/json")
    public String forgotPassword(@RequestParam String mail) throws SQLException {
        return userService.forgotPassword(mail);
    }

    @PostMapping(value = "/reset-password", produces = "application/json")
    public String resetPassword(@RequestParam String hash, @RequestParam String password) throws NoSuchAlgorithmException, JsonProcessingException {
        return userService.resetPassword(hash, password);
    }

    @GetMapping(value = "/user-detail", produces = "application/json")
    public User getUser(@RequestHeader("Authorization") String authorizationHeader) throws SQLException {
        int userId = jwtService.decodeJwtAndGetUserId(authorizationHeader);
        return userService.get(userId);
    }

    @PostMapping(value = "/user-update", produces = "application/json")
    public String updateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody User userRequest) {
        int userId = jwtService.decodeJwtAndGetUserId(authorizationHeader);
        return userService.updateUser(userRequest, userId);
    }

}