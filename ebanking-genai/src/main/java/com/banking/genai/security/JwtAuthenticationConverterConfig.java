package com.banking.genai.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
public class JwtAuthenticationConverterConfig {

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                jwtAuthoritiesConverter()
        );

        return converter;
    }

    private Converter<Jwt, Collection<GrantedAuthority>>
    jwtAuthoritiesConverter() {

        return jwt -> {

            List<String> roles = jwt.getClaimAsStringList("roles");

            if (roles == null || roles.isEmpty()) {
                return Collections.emptyList();
            }

            return roles.stream()
                    .map(String::trim)
                    .filter(role -> !role.isBlank())
                    .map(role -> role.startsWith("ROLE_")
                            ? role
                            : "ROLE_" + role)
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast)
                    .toList();
        };
    }
}