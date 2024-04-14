package com.oop.api.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a ticketing officer who can manage ticket sales.
 */
@Entity
@Getter @Setter
public class TicketingOfficer extends User {
  
    
}