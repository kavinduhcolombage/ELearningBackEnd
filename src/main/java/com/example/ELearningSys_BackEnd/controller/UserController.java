package com.example.ELearningSys_BackEnd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ELearningSys_BackEnd.model.User;
import com.example.ELearningSys_BackEnd.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable  int id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent())
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);        
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user); 
    }
    
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

}