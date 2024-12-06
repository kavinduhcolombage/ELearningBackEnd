package com.example.ELearningSys_BackEnd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.ELearningSys_BackEnd.jwt.JwtFilter;
import com.example.ELearningSys_BackEnd.service.UserService;

@Configuration
public class SecurityConfig {
    private final UserService userService;
    private final JwtFilter jwtFilter;
    
}
