package com.example.controller;

import com.example.dao.UserRepository;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
      
        com.example.entity.Role userRole = user.getRole();
        if (userRole == null) {
            user.setRole(com.example.entity.Role.USER);
            user = userRepository.save(user);
            userRole = user.getRole(); // Get the saved role
        }
        
   
        String roleName = (userRole != null) ? userRole.name() : "USER";
        
        return ResponseEntity.ok(Map.of(
            "id", user.getId(),
            "name", user.getName() != null ? user.getName() : "",
            "email", user.getEmail(),
            "role", roleName,
            "message", "Profile fetched successfully!"
        ));
    }
}
