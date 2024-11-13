package com.example.ELearningSys_BackEnd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ELearningSys_BackEnd.model.User;
import com.example.ELearningSys_BackEnd.repository.UserRepository;

@Service
public class UserService {
  
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // Get a user by ID
    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }

    //Create user
    public User createUser(User user){
        return userRepository.save(user);
    }
    
    //update user
    public User updateUser(User user){

        Optional<User> existingUserOptional = userRepository.findById(user.getId());
        
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setUserName(user.getUserName());
            existingUser.setPassWord(user.getPassWord());
            existingUser.setEmail(user.getEmail());
            existingUser.setRole(user.getRole());
            //save updated entity
            return userRepository.save(existingUser);
        }else{
            return null;
        }   
    }

    //delete user by ID
    public void deleteUser(int id){
        userRepository.deleteById(id);
    }
}
