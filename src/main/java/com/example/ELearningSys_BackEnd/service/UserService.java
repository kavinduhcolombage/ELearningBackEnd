package com.example.ELearningSys_BackEnd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ELearningSys_BackEnd.model.Role;
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

    //sign up(register new user)
    public ResponseEntity<String> signup(User user) {
        try {
            if(validateUser(user)){
                if (!checkUserNameExist(user)) {
                    user.setRole(Role.STUDENT);
                    userRepository.save(user);
                    return new ResponseEntity<>("Succesfully add new user", HttpStatus.OK);
                }else
                    return new ResponseEntity<>("user already exist", HttpStatus.CONFLICT); 
            }else{
                return new ResponseEntity<>("Invalid Data", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);     
    }

    //checking username is exist or not
    private boolean checkUserNameExist(User user){
        try {
            if(userRepository.findByUserName(user.getUserName()).isEmpty()){
                return false;
            }else{
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;           
    }

    //check fields of data
    private boolean validateUser(User user){
        if(user.getId() != 0 && user.getUserName() != null && user.getPassWord() != null && user.getEmail() != null)
            return true;
        else
            return false;
    }
}
