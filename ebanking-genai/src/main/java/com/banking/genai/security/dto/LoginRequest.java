package com.banking.genai.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LoginRequest(

        @NotBlank(message = "Username is required")
        @Size(max = 100, message = "Username cannot exceed 100 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(max = 72, message = "Password cannot exceed 72 characters")
        String password,

        @NotNull(message = "Tenant ID is required")
        UUID tenantId

) {
}