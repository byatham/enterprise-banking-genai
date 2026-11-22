package com.banking.genai.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/v1/health")
    public Map<String, String> health() {

        return Map.of(
            "status", "UP",
            "service", "banking-genai-api",
            "version", "1.0.0"
        );
    }
}