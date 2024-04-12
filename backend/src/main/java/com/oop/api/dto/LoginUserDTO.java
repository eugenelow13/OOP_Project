package com.oop.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a data transfer object (DTO) for a login user.
 * It contains the email and password of the user.
 */
@Getter @Setter
public class LoginUserDTO {
    private String email;
    private String password;
}
