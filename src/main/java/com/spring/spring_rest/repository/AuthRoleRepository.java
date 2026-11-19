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

  /**
   * Finds a role by name.
   *
   * @param theName the role name
   * @return the matching role, if present
   */
  Optional<Auth_Role> findByName(String theName);
  
  /**
   * Checks whether a role with the supplied name exists.
   *
   * @param theUsername the role name to check
   * @return {@code true} if the role exists
   */
  Boolean existsByName(String theUsername);
}
