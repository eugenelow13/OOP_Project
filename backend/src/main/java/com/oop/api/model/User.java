package com.oop.api.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity // This tells Hibernate to make a table out of this class
@Getter @Setter
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(nullable = false, updatable = false)
  private Integer id;

  @Column(nullable = false)
  private String fullName;

  @Email
  @Column(unique = true, length = 100, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = true)
  private double creditBalance;
  
  // Getters and setters
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of();
  }

  public String getPassword() {
      return password;
  }

  @Override
  public String getUsername() {
      return email;
  }

  @Override
  public boolean isAccountNonExpired() {
      return true;
  }

  @Override
  public boolean isAccountNonLocked() {
      return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
      return true;
  }

  @Override
  public boolean isEnabled() {
      return true;
  }
  
  // Getters and setters
  public Integer getId() {
    return id;
  }

  public String getFullName() {
      return fullName;
  }

  public String getEmail() {
      return email;
  }

  public void setFullName(String fullName) {
      this.fullName = fullName;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public void setPassword(String password) {
      this.password = password;
  }
}