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

    // Create user by admin
    public ResponseEntity<String> createUser(User user) {
        try {
            if (validUserDetails(user) && validEmail(user) && validPassword(user)) {
                System.out.println("admin enter valid user detials");
                if (!isEmailAlreadyInUse(user)) {
                    if (isValidRole(user.getRole())) {
                        user.setPassword(encoder.encode(user.getPassword()));
                        userRepository.save(user);
                        return new ResponseEntity<>("User Created successfully", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("Invalid Role", HttpStatus.BAD_REQUEST);
                    }
                } else
                    return new ResponseEntity<>("Email already Used", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("Invalid details", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // register new user
    public ResponseEntity<String> registerUser(User user) {
        try {
            if (validUserDetails(user) && validEmail(user) && validPassword(user)) {
                if (!isEmailAlreadyInUse(user)) {
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

    // log user
    public ResponseEntity<String> login(User user) {
        try {
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                Optional<User> existingUser = userRepository.findByEmail(userDetails.getUsername());
                System.out.println("logged User Role: " + existingUser.get().getRole().name());
                String token = jwtService.generateToken(user.getEmail(), existingUser.get().getRole().name());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by ID
    public ResponseEntity<User> getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);        
    }

    // Get a user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // update user
    public ResponseEntity<User> updateUser(User user, String email) {
        Optional<User> existingUserOptional = userRepository.findByEmail(email);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            if(validUserDetails(user)){
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                //existingUser.setPassword(encoder.encode(user.getPassword()));
                // save updated entity
                userRepository.save(existingUser);
                return new ResponseEntity<>(existingUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete user by ID
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    // check role is valid or not
    private boolean isValidRole(Role role) {
        for (Role r : Role.values()) {
            if (r == role) {
                return true;
            }
        }
        return false;
    }

    // validate user details
    private boolean validUserDetails(User user) {
        return user.getFirstName() != null && user.getLastName() != null;
    }

    private boolean validEmail(User user) {
        return user.getEmail() != null && !user.getEmail().isEmpty();
    }

    // validate user password
    private boolean validPassword(User user) {
        return user.getPassword() != null && !user.getPassword().isEmpty();
    }

    // checking user email is exist or not
    private boolean isEmailAlreadyInUse(User user) {
        try {
            return userRepository.findByEmail(user.getEmail()).isPresent();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
