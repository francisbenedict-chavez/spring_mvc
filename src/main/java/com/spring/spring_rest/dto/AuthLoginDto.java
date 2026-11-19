package com.spring.spring_rest.dto;

/**
 * Represents an application user and the roles assigned to that user.
 */
public class AuthLoginDto {

  private String username;
  private String password;

  /**
   * Creates login credentials with the supplied username and password.
   *
   * @param username the user's username
   * @param password the user's password
   */
  public AuthLoginDto(String username, String password) {
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
