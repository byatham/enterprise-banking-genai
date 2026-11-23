package com.banking.genai.auth;


import com.banking.genai.security.JwtTokenService;
import com.banking.genai.security.dto.LoginRequest;
import com.banking.genai.security.dto.LoginResponse;
import com.banking.genai.user.BankingUserDetailsService;
import com.banking.genai.user.BankingUserPrincipal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthService {

    private final BankingUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final long expirationSeconds;

    public AuthService(
            BankingUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService,
            @Value("${security.jwt.expiration-minutes:15}")
            long expirationMinutes
    ) {
        if (expirationMinutes <= 0) {
            throw new IllegalArgumentException(
                    "JWT expiration must be positive"
            );
        }

        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.expirationSeconds = Math.multiplyExact(
                expirationMinutes, 60L
        );
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        // 1. Retrieve user using tenant-aware lookup
        BankingUserPrincipal principal =
                userDetailsService.loadUserByUsernameAndTenantId(
                        request.username(),
                        request.tenantId()
                );

        // 2. Validate account and password
        if (!principal.isEnabled()
                || !passwordEncoder.matches(
                        request.password(),
                        principal.getPassword()
                )) {
            throw new BadCredentialsException(
                    "Invalid username or password"
            );
        }

        // 3. Extract roles
        List<String> roles = principal.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority()
                        .replaceFirst("^ROLE_", ""))
                .sorted()
                .toList();

        // 4. Generate JWT
        String accessToken = jwtTokenService.generateToken(
                principal.getUserId(),
                principal.getUsername(),
                principal.getTenantId(),
                roles
        );

        // 5. Return authentication response
        return new LoginResponse(
                accessToken,
                "Bearer",
                expirationSeconds,
                principal.getUserId(),
                principal.getUsername(),
                principal.getTenantId(),
                roles
        );
    }
}
