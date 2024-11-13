package com.example.ELearningSys_BackEnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ELearningSys_BackEnd.model.User;
import com.example.ELearningSys_BackEnd.repository.UserRepository;

@Service
public class UserService {
  
    @Autowired
    private UserRepository userRepository;

    
    
}
