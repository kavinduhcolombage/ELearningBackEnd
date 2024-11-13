package com.example.ELearningSys_BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ELearningSys_BackEnd.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
