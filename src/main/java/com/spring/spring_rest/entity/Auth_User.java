package com.spring.spring_rest.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an application user and the roles assigned to that user.
 */
@Entity
@Table(name = "auth_user")
public class Auth_User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "authusers_authroles", 
      joinColumns = @JoinColumn(
      name = "authuser_id", referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(
      name = "authrole_id", referencedColumnName = "id"))
  private List<Auth_Role> roles = new ArrayList<>();

  public Auth_User() {}

  /**
   * Creates an authorization user with the specified 
   * identity, credentials, and roles.
   *
   * @param id the user's identifier
   * @param username the user's username
   * @param password the user's password
   * @param roles the roles assigned to the user
   */
  public Auth_User(Long id, String username, String password, List<Auth_Role> roles) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public List<Auth_Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Auth_Role> roles) {
    this.roles = roles;
  }
  
}
