package com.example.study.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// dto/CreateUserReq.java
public record CreateUserReq(@NotBlank String username,@NotBlank String password,
                            @NotBlank String name,@Email String email) {}