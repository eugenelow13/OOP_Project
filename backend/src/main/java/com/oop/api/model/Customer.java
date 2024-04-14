package com.oop.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a customer of the ticketing system.
 */
@Entity
@Getter @Setter
public class Customer extends User{ 

    @Column(nullable = false)
    private double creditBalance = 1000.0; // Default credit balance of $1000

    
}
