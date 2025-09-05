package com.example.study.users.controller;

import com.example.study.users.dto.CreateUserReq;
import com.example.study.users.dto.UserDto;
import com.example.study.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody CreateUserReq req){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(req));
    }

    @GetMapping("{id}")
    public UserDto get(@PathVariable Long id){ return userService.get(id); }

    @PutMapping("{id}")
    public UserDto update(@PathVariable Long id, @Valid @RequestBody CreateUserReq req){
        return userService.update(id, req);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id); return ResponseEntity.noContent().build();
    }
}

