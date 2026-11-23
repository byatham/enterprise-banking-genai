package com.banking.genai.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BankingUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public BankingUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Standard Spring Security lookup.
     *
     * We don't use this method for tenant-aware login because
     * username alone is not globally unique in our platform.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {

        throw new UnsupportedOperationException(
                "Use tenant-aware authentication"
        );
    }

    /**
     * Loads a user using username and tenant ID.
     */
    @Transactional(readOnly = true)
    public BankingUserPrincipal loadUserByUsernameAndTenantId(
            String username,
            UUID tenantId
    ) {

        User user = userRepository
                .findByUsernameAndTenantId(username, tenantId)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Invalid username or credentials"
                        )
                );

        return new BankingUserPrincipal(user);
    }
}