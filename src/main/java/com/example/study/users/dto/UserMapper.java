package com.example.study.users.dto;

import com.example.study.users.entity.Users;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;

// 간단 매퍼
public final class UserMapper {
    public static UserDto toDto(Users u){
        return new UserDto(u.getUserId(), u.getUsername(), u.getName(), u.getEmail(), u.getRole());
    }
    public static Users from(CreateUserReq r){
        Users u = new Users();
        u.setUsername(r.username());
        u.setPassword(r.password()); // 실제 환경에선 암호화 필요
        u.setName(r.name());
        u.setEmail(r.email());
        u.setRole("USER");
        u.setCreatedAt(Instant.now());
        u.setUpdatedAt(Instant.now());
        return u;
    }
}
