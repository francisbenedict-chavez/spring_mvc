package com.spring.spring_rest.repository;

import com.spring.spring_rest.entity.Auth_Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides database access and name queries for authorization roles.
 *
 * @see Auth_Role
 */
public interface AuthRoleRepository extends JpaRepository<Auth_Role, Long> {

  Optional<Auth_Role> findByName(String theName);

  Boolean existsByName(String theUsername);
}
