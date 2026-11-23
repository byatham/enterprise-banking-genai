package com.banking.genai.security.dto;


import java.util.List;
import java.util.UUID;

public record LoginResponse(

        String accessToken,

        String tokenType,

        long expiresIn,

        UUID userId,

        String username,

        UUID tenantId,

        List<String> roles

) {

    public LoginResponse {
        roles = List.copyOf(roles);
    }
}