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

  /**
   * Finds a user by username.
   *
   * @param theUsername the username
   * @return the matching user, if present
   */
  Optional<Auth_User> findByUsername(String theUsername);

  /**
   * Checks whether a username is already registered.
   *
   * @param theUsername the username to check
   * @return {@code true} if the username exists
   */
  Boolean existsByUsername(String theUsername);
}
