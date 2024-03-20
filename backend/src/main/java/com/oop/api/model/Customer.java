package com.oop.api.model;

import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Getter @Setter
public class Customer extends User{ 
    // @NotNull
    // private String password;
    
    @Column(nullable = false)
    private double creditBalance = 1000.0; // Default credit balance of $1000

    @Column(nullable = false)
    private List<Booking> bookingHistory;

    // @ElementCollection // store an array of tickets for each event
    // private List<Ticket> tickets = new ArrayList<>();

    
}
