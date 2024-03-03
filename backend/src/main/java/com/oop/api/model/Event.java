package com.oop.api.model;

import java.time.LocalDateTime;
import java.util.List;

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
public class Event {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;  

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private String venue;

    @NotNull
    private LocalDateTime date;

    private double ticketPrice;

    private double cancellationFee;

    private int noOfTicketsAvailable;

    private int customerAttendance;

}
