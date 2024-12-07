package com.example.ELearningSys_BackEnd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.ELearningSys_BackEnd.jwt.JwtFilter;
import com.example.ELearningSys_BackEnd.service.UserService;

@Configuration
public class SecurityConfig {
    private final UserService userService;
    private final JwtFilter jwtFilter;

    
    public SecurityConfig(UserService userService, JwtFilter jwtFilter) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    //     return http.getSharedObject(AuthenticationManagerBuilder.class)
    //         .userService(userService)
    //         .passwordEncoder(passwordEncoder())
    //         .and()
    //         .build();
    // }


    // @Bean
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.csrf().disable()
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers("/auth/**").permitAll() 
    //             .anyRequest().authenticated())
    //         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    // }


    
}
