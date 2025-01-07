package com.example.ELearningSys_BackEnd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ELearningSys_BackEnd.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    //custom Jpa method using naming convention
    User findByEmail(String email);
    User findByUsername(String username);
}
