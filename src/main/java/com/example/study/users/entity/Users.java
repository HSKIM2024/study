package com.example.study.users.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

// Users.java
@Entity @Table(name="users")
@Getter @Setter
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String name;
    private String email;
    private String role;
    private String interestTags;
    private Double latitude;
    private Double longitude;
    private Instant createdAt;
    private Instant updatedAt;
}

