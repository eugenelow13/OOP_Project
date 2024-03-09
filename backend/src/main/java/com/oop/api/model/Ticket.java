package com.oop.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Ticket {
    
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull
    @PositiveOrZero
    @Max(value = 4, message = "Number of guests cannot exceed 4")
    private Integer noOfGuests = 0;

    private Boolean isAdmitted = false;

}
