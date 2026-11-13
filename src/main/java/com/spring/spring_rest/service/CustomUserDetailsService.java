package com.spring.spring_rest.service;

import com.spring.spring_rest.entity.Auth_Role;
import com.spring.spring_rest.entity.Auth_User;
import com.spring.spring_rest.repository.AuthUserRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Provides user details for Spring Security authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final AuthUserRepository authUserRepository;

  /**
   * Creates the service with the repository used to find users.
   *
   * @param authUserRepository repository used to look up user accounts
   */
  public CustomUserDetailsService(AuthUserRepository authUserRepository) {
    this.authUserRepository = authUserRepository;
  }

  /**
   * Loads a user and their authorities by username.
   *
   * @param theUsername username to look up
   * @return the user's details for Spring Security
   * @throws UsernameNotFoundException if no user has that username
   */
  @Override
  public UserDetails loadUserByUsername(String theUsername) {
    Auth_User user = authUserRepository.findByUsername(theUsername)
        .orElseThrow(
            () -> new UsernameNotFoundException("Username "
                + theUsername + " does not exist"));

    return new User(user.getUsername(),
        user.getPassword(), mapRolesToAuthorities(user.getRoles()));
  }

  /**
   * Converts roles into Spring Security authorities.
   *
   * @param roles roles assigned to the user
   * @return the roles as granted authorities
   */
  private Collection<GrantedAuthority> 
      mapRolesToAuthorities(List<Auth_Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }

}
