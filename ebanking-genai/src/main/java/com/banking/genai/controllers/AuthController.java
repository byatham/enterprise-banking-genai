package com.banking.genai.controllers;

import com.banking.genai.security.dto.LoginRequest;
import com.banking.genai.security.dto.LoginResponse;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
    
    @GetMapping("/analytics")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    public String getAnalytics() {
        return "Banking analytics";
    }
}