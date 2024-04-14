package com.oop.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents an event that can be attended by customers.
 */
@ToString
@Entity
@Getter @Setter
public class Event {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;  

    @NotNull
    private String name;

    @NotNull
    private String type;

    private String imageUrl;

    private String description;

    @NotNull
    private String venue;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private double ticketPrice;

    @NotNull
    private double cancellationFee;

    @NotNull
    private int ticketsAvailable;

    private int customerAttendance;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;
}
