package com.example.ELearningSys_BackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ELearningSys_BackEnd.model.User;
import com.example.ELearningSys_BackEnd.model.UserPriciple;
import com.example.ELearningSys_BackEnd.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null){
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }
        // need to return user details, for that userpriciple class created
        return new UserPriciple(user);
        
    }

}
