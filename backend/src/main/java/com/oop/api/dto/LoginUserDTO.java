package com.oop.api.dto;

/**
 * This class represents a data transfer object (DTO) for a login user.
 * It contains the email and password of the user.
 */

public class LoginUserDTO {
    private String email;
    
    private String password;
    
    // getters and setters here...
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
