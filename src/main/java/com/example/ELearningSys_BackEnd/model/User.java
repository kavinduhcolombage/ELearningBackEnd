package com.example.ELearningSys_BackEnd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    private int id;
    private String userName;
    private String passWord;
    private String email;
    private Role role;
}
