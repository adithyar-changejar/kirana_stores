package com.example.kiranastore.auth;

import com.example.kiranastore.dto.LoginRequestDTO;
import com.example.kiranastore.dto.LoginResponseDTO;
import com.example.kiranastore.dto.RegisterRequestDTO;
import com.example.kiranastore.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Auth controller.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Instantiates a new Auth controller.
     *
     * @param authService the auth service
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Register response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * Login response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
