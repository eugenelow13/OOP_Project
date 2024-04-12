package com.oop.api.dto;

import com.oop.api.model.Role;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class LoginResponseDTO {
    private int id;
    private String token;
    private long expiresIn;
    private String fullName;
    private String email;
    private Set<Role> roles;
}