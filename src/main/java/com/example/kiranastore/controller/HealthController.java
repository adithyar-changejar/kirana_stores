package com.example.kiranastore.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The type Health controller.
 */
@RestController
public class HealthController {

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        System.out.println("Healthcontroller loaded");
    }

    /**
     * Health string.
     *
     * @return the string
     */
    @GetMapping("/health")
    public String health() {
        return "OK";
    }

}
