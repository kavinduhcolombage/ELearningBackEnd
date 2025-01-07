package com.example.ELearningSys_BackEnd.security;

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

    //load user by email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null){
            System.out.println("email not found");
            throw new UsernameNotFoundException("email not found");
        }
        // need to return user details, for that userpriciple class created
        return new UserPriciple(user);
        
    }

}
