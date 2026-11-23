package com.banking.genai.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TenantContext {

    public UUID getCurrentTenantId() {

        var authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)
                || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException(
                    "Authenticated user required"
            );
        }

        return UUID.fromString(
                jwtAuth.getToken().getClaimAsString("tenant_id")
        );
    }

    public UUID getCurrentUserId() {

        var authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)
                || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException(
                    "Authenticated user required"
            );
        }

        return UUID.fromString(
                jwtAuth.getToken().getClaimAsString("user_id")
        );
    }
}