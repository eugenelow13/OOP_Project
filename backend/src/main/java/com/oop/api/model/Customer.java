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

@Entity
@Table(name="customer")
@Getter @Setter
public class Customer extends User{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Email
    @NotNull
    private String email;
    
    @NotNull
    private String password;
    
    public Customer(String name, String email, String password){
        super(name, email);
        this.password = password;
    }

    
}
