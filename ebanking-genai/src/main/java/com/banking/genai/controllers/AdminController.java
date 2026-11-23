package com.banking.genai.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping("/reports")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getReports() {
        return ResponseEntity.ok("Admin reports");
    }

    @GetMapping("/analytics")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    public ResponseEntity<String> getAnalytics() {
        return ResponseEntity.ok("Banking analytics");
    }
}