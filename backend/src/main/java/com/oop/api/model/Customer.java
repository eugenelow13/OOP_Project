package com.oop.api.model;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Customer extends User{ 

    @Column(nullable = false)
    private double creditBalance = 1000.0; // Default credit balance of $1000

    // @ElementCollection // store an array of tickets for each event
    // private List<Ticket> tickets = new ArrayList<>();

    
}
