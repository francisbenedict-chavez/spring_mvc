package com.spring.spring_rest.repository;

import com.spring.spring_rest.entity.Auth_User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides database access and username queries for user accounts.
 *
 * @see Auth_User
 */
public interface AuthUserRepository extends JpaRepository<Auth_User, Long> {

  Optional<Auth_User> findByUsername(String theUsername);

  Boolean existsByUsername(String theUsername);
  
}
