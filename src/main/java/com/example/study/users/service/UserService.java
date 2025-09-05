package com.example.study.users.service;

import com.example.study.common.exception.NotFoundException;
import com.example.study.users.dto.CreateUserReq;
import com.example.study.users.dto.UserDto;
import com.example.study.users.dto.UserMapper;
import com.example.study.users.entity.Users;
import com.example.study.users.repository.UsersRepository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service @RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    @Transactional
    public UserDto create(CreateUserReq req){
        Users saved = usersRepository.save(UserMapper.from(req));
        return UserMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public UserDto get(Long id){
        return usersRepository.findById(id).map(UserMapper::toDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public UserDto update(Long id, CreateUserReq req){
        Users u = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        u.setName(req.name()); u.setEmail(req.email()); u.setUpdatedAt(Instant.now());
        return UserMapper.toDto(u);
    }

    @Transactional
    public void delete(Long id){ usersRepository.deleteById(id); }
}

