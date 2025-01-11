package com.example.ELearningSys_BackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ELearningSys_BackEnd.model.User;
import com.example.ELearningSys_BackEnd.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> signup(@RequestBody User user){
        try {
            return userService.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        return userService.login(user);
    }
}
