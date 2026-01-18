package com.example.kiranastore.controller;

import com.example.kiranastore.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/users/{userId}/promote")
    public ResponseEntity<String> promoteUser(@PathVariable String userId) {
        adminService.promoteToAdmin(userId);
        return ResponseEntity.ok("User promoted to ADMIN");
    }
}
