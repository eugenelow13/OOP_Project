package com.oop.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a data transfer object (DTO) for registering a user.
 * It contains the user's email, password, and full name.
 */

@Getter @Setter
public class RegisterUserDTO {
    private String email;
    private String password;
    private String fullName;
    
}
