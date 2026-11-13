package com.spring.spring_rest.controller;

import com.spring.spring_rest.dto.AuthLoginDto;
import com.spring.spring_rest.dto.AuthRoleRegisterDto;
import com.spring.spring_rest.dto.AuthUserRegisterDto;
import com.spring.spring_rest.entity.Auth_Role;
import com.spring.spring_rest.entity.Auth_User;
import com.spring.spring_rest.repository.AuthRoleRepository;
import com.spring.spring_rest.repository.AuthUserRepository;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides endpoints for registering roles and users and authenticating users.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  private AuthenticationManager authenticationManager;
  private AuthUserRepository authUserRepository;
  private AuthRoleRepository authRoleRepository;
  private BCryptPasswordEncoder passwordEncoder;

  /**
   * Creates the controller with the authentication 
   * and persistence dependencies.
   *
   * @param authenticationManager manager used to authenticate credentials
   * @param authUserRepository    repository for user accounts
   * @param authRoleRepository    repository for authorization roles
   * @param passwordEncoder       encoder used to hash passwords
   */
  public AuthenticationController(AuthenticationManager authenticationManager,
      AuthUserRepository authUserRepository,
      AuthRoleRepository authRoleRepository,
      BCryptPasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.authUserRepository = authUserRepository;
    this.authRoleRepository = authRoleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Registers a role unless a role with the same name already exists.
   *
   * @param authRoleRegisterDto details of the role to register
   * @return the result of the registration and its HTTP status
   */
  @PostMapping("/register_authorized_role")
  public ResponseEntity<String> register_authRole(
      @RequestBody AuthRoleRegisterDto authRoleRegisterDto) {
    if (authRoleRepository.existsByName(authRoleRegisterDto.getName())) {
      return new ResponseEntity<>(authRoleRegisterDto.getName()
          + " already created.", HttpStatus.BAD_REQUEST);
    }

    Auth_Role auth_role = new Auth_Role();
    auth_role.setName(authRoleRegisterDto.getName());

    authRoleRepository.save(auth_role);

    return new ResponseEntity<>(auth_role.getName()
        + " is successfully created.", HttpStatus.OK);
  }

  /**
   * Registers a user with an encoded password and the default user role.
   *
   * @param authUserRegisterDto details of the user to register
   * @return the result of the registration and its HTTP status
   */
  @PostMapping("/register_authorized_user")
  public ResponseEntity<String> register_authUser(
      @RequestBody AuthUserRegisterDto authUserRegisterDto) {
    if (authUserRepository.existsByUsername(
        authUserRegisterDto.getUsername())) {
      return new ResponseEntity<>(authUserRegisterDto.getUsername()
          + " is already taken.", HttpStatus.BAD_REQUEST);
    }

    Auth_User auth_user = new Auth_User();
    auth_user.setUsername(authUserRegisterDto.getUsername());
    auth_user.setPassword(passwordEncoder.encode(
        authUserRegisterDto.getPassword()));

    Auth_Role auth_role = authRoleRepository.findByName("USER")
        .orElseThrow(() -> new RuntimeException("Roles USER not found."));
    auth_user.setRoles(Collections.singletonList(auth_role));

    authUserRepository.save(auth_user);

    return new ResponseEntity<>(auth_user.getUsername()
        + " is successfully registered", HttpStatus.OK);
  }

  /**
   * Authenticates the supplied username and password.
   *
   * @param authLoginDto the user's login credentials
   * @return the authentication result and its HTTP status
   */
  @PostMapping("/auth_login")
  public ResponseEntity<String> login(@RequestBody AuthLoginDto authLoginDto) {
    Authentication authenticate = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authLoginDto.getUsername(),
            authLoginDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authenticate);

    return new ResponseEntity<>("Sign in successfully!", HttpStatus.OK);
  }
}
