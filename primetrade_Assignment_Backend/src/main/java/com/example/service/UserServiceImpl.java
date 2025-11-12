package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dao.UserRepository;
import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User register(UserDto userDto) {

        // Check if email already exists
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Create new user
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // Set role: use provided role or default to USER
        user.setRole(userDto.getRole() != null ? userDto.getRole() : Role.USER);

        // Save user in DB
        return userRepository.save(user);
    }

    @Override
    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email or Password"));

        // Verify password
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        // Ensure user has a role (default to USER if null for existing users)
        Role userRole = user.getRole() != null ? user.getRole() : Role.USER;
        
        // Update user role if it was null (for existing users)
        if (user.getRole() == null) {
            user.setRole(userRole);
            userRepository.save(user);
        }

        // Create Spring Security user details
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(userRole.name()) 
                .build();

        // Generate JWT token
        return jwtUtil.generateToken(userDetails);
    }
}
