package com.spring.spring_rest.dto;

/**
 * Holds the username and password supplied when registering a user.
 */
public class AuthUserRegisterDto {

  private String username;
  private String password;
  
  /**
   * Creates user registration data.
   *
   * @param username the username to register
   * @param password the password to register
   */
  public AuthUserRegisterDto(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
