package com.spring.spring_rest.dto;

/**
 * Holds the role name supplied when registering a role.
 */
public class AuthRoleRegisterDto {

  private String name;
  
  /**
   * Creates role registration data with the supplied name.
   *
   * @param name the role name
   */
  public AuthRoleRegisterDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
