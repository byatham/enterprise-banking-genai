package com.banking.genai.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user by username and tenant.
     * Loads roles eagerly for authentication.
     */
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUsernameAndTenantId(
            String username,
            UUID tenantId
    );

    /**
     * Finds a user by ID and tenant.
     */
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByIdAndTenantId(
            UUID id,
            UUID tenantId
    );

    /**
     * Checks whether a username already exists
     * within a specific tenant.
     */
    boolean existsByUsernameAndTenantId(
            String username,
            UUID tenantId
    );
}