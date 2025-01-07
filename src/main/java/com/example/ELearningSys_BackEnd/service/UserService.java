package com.example.ELearningSys_BackEnd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ELearningSys_BackEnd.model.Role;
import com.example.ELearningSys_BackEnd.model.User;
import com.example.ELearningSys_BackEnd.model.UserPriciple;
import com.example.ELearningSys_BackEnd.repository.UserRepository;
import com.example.ELearningSys_BackEnd.security.JWTService;

@Service
public class UserService {
  
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // Get a user by ID
    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }

    //Create user
    public User createUser(User user){ 
        user.setRole(Role.STUDENT);
        user.setPassword(encoder.encode(user.getPassword()));   
        return userRepository.save(user);
    }
    
    //update user
    public User updateUser(User user){

        Optional<User> existingUserOptional = userRepository.findById(user.getId());
        
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
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

    //register new user
    public ResponseEntity<String> registerUser(User user) {
        try {
            if(validateUser(user)){
                if (!checkUserEmailExist(user)) {
                    user.setRole(Role.STUDENT);
                    user.setPassword(encoder.encode(user.getPassword()));
                    userRepository.save(user);
                    return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
                }else
                    return new ResponseEntity<>("Email already registered", HttpStatus.CONFLICT); 
            }else{
                return new ResponseEntity<>("Invalid details", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);     
    }

    //checking user email is exist or not
    private boolean checkUserEmailExist(User user){
        try {
            if(userRepository.findByEmail(user.getEmail()) == null){
                return false;
            }else{
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }          
    }

    //check fields of data
    private boolean validateUser(User user){
        if(user.getFirstName() != null && user.getPassword() != null && user.getEmail() != null)
            return true;
        else
            return false;
    }

    public ResponseEntity<String> login(User user){
        try {
            Authentication authentication =  
            authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(user.getEmail());
                return ResponseEntity.ok(token);
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (AuthenticationException e) { 
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }        
    }

}
