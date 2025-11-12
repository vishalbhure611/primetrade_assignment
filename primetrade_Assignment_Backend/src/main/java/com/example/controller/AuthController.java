package com.example.controller;

import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*") 
public class AuthController {

    @Autowired
    private UserService userService;

    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
        User savedUser = userService.register(userDto);
        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "user", savedUser
        ));
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        String token = userService.login(loginDto);
        return ResponseEntity.ok(Map.of(
                "token", token,
                "message", "Login successful"
        ));
    }
}
