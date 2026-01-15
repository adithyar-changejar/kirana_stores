package com.example.kiranastore.service;

import com.example.kiranastore.auth.JwtTokenProvider;
import com.example.kiranastore.dto.LoginRequestDTO;
import com.example.kiranastore.dto.LoginResponseDTO;
import com.example.kiranastore.dto.RegisterRequestDTO;
import com.example.kiranastore.entity.UserRole;
import com.example.kiranastore.mongo.UserDocument;
import com.example.kiranastore.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(
            UserRepository userRepository,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    // REGISTER

    public void register(RegisterRequestDTO request) {

        // Check if user already exists
        if (userRepository.existsById(request.getUserId())) {
            throw new IllegalArgumentException("User already exists");
        }

        // Create user document
        UserDocument user = new UserDocument();
        user.setUserId(request.getUserId());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.valueOf(request.getRole()));
        user.setCreatedAt(new Date());

        // Save user
        userRepository.save(user);
    }


    // LOGIN

    public LoginResponseDTO login(LoginRequestDTO request) {

        // Fetch user
        UserDocument user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid credentials"));

        // Validate password (CORRECT WAY)
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        // Generate JWT
        String token = jwtTokenProvider.generateToken(
                user.getUserId(),
                user.getRole().name()
        );

        // Return response
        return new LoginResponseDTO(
                token,
                "Bearer",
                jwtTokenProvider.getExpiry()
        );
    }
}
