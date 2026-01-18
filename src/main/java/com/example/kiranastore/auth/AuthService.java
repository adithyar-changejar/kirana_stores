package com.example.kiranastore.service;

import com.example.kiranastore.auth.JwtTokenProvider;
import com.example.kiranastore.dto.LoginRequestDTO;
import com.example.kiranastore.dto.LoginResponseDTO;
import com.example.kiranastore.dto.RegisterRequestDTO;
import com.example.kiranastore.entity.UserRole;
import com.example.kiranastore.mongo.UserDocument;
import com.example.kiranastore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ REGISTER — ALWAYS USER
    public void register(RegisterRequestDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        UserDocument user = new UserDocument();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        userRepository.save(user);
    }


    // ✅ LOGIN
    public LoginResponseDTO login(LoginRequestDTO request) {

        UserDocument user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));


        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(
                user.getId().toHexString(),
                user.getRole().name()
        );

        return new LoginResponseDTO(
                token,
                "Bearer",
                jwtTokenProvider.getExpiry()
        );
    }

}
