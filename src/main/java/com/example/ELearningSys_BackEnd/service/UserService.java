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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ELearningSys_BackEnd.model.Role;
import com.example.ELearningSys_BackEnd.model.User;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Create user
    public ResponseEntity<String> createUser(User user) {
        try {
            if (validateUser(user)) {
                if (!checkUserEmailExist(user)) {
                    if (isValidRole(user.getRole())) {
                        user.setPassword(encoder.encode(user.getPassword()));
                        userRepository.save(user);
                        return new ResponseEntity<>("User Created successfully", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("Invalid role", HttpStatus.BAD_REQUEST);
                    }
                } else
                    return new ResponseEntity<>("Email already Used", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("Invalid details", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean isValidRole(Role role) {
        for (Role r : Role.values()) {
            if (r == role) {
                return true;
            }
        }
        return false;
    }



    // update user
    public ResponseEntity<User> updateUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(encoder.encode(user.getPassword()));
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            // save updated entity
            userRepository.save(existingUser);
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete user by ID
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    // register new user
    public ResponseEntity<String> registerUser(User user) {
        try {
            if (validateUser(user)) {
                if (!checkUserEmailExist(user)) {
                    user.setRole(Role.STUDENT);
                    user.setPassword(encoder.encode(user.getPassword()));
                    userRepository.save(user);
                    return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
                } else
                    return new ResponseEntity<>("Email already registered", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("Invalid details", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // checking user email is exist or not
    private boolean checkUserEmailExist(User user) {
        try {
            if (userRepository.findByEmail(user.getEmail()) == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // check fields of data
    private boolean validateUser(User user) {
        if (user.getFirstName() != null && user.getPassword() != null && user.getEmail() != null)
            return true;
        else
            return false;
    }

    public ResponseEntity<String> login(User user) {
        try {
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                User existingUser = userRepository.findByEmail(userDetails.getUsername());
                System.out.println("User Role: " + existingUser.getRole().name());
                String token = jwtService.generateToken(user.getEmail(), existingUser.getRole().name());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
