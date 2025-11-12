package com.example.service;

import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.entity.User;

public interface UserService {
	User register(UserDto userDto);
	String login(LoginDto loginDto);
	
}
