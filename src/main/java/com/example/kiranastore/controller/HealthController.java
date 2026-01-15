package com.example.kiranastore.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
public class HealthController {

    @PostConstruct
    public void init() {
        System.out.println("Healthcontroller loaded");
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

}
