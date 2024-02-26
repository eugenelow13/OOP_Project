package com.oop.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="user")
@Getter @Setter
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @NotNull
  private String name;

  @Email
  @Column(name="email", unique=true)
  private String email;

  // public Integer getId() {
  //   return id;
  // }

  // public void setId(Integer id) {
  //   this.id = id;
  // }

  // public String getName() {
  //   return name;
  // }

  // public void setName(String name) {
  //   this.name = name;
  // }

  // public String getEmail() {
  //   return email;
  // }

  // public void setEmail(String email) {
  //   this.email = email;
  // }
}