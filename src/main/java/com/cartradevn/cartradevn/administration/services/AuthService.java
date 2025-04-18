package com.cartradevn.cartradevn.administration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cartradevn.cartradevn.administration.dto.LoginDTO;
import com.cartradevn.cartradevn.administration.dto.RegisterDTO;
import com.cartradevn.cartradevn.administration.entity.User;
import com.cartradevn.cartradevn.administration.respository.UserRepo;

import lombok.extern.java.Log;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterDTO registerDTO) {
        // Check if the username or email already exists
        if (userRepo.existsByUsername(registerDTO.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        if (userRepo.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        // Create a new user entity and set its properties
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(registerDTO.getRole());

        // Save the user to the database
        return userRepo.save(user);
    }

    public User login(LoginDTO loginDTO) {
        // Find the user by username
        User user = userRepo.findByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new RuntimeException("Tên đăng nhập không tồn tại");
        }

        // Check if the password is correct
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        return user;
    }
}
