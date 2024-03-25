package com.oop.api.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private String eventStatus;





    // @OneToMany(mappedBy = "event")
    // private List<Booking> bookings; 

    // public void addBooking (Booking newBooking) {
    //     this.bookings.add(newBooking);
    // }

    // public void setCancellationFee(double newCancellationFee) {
    //     this.cancellationFee = newCancellationFee;
    // }

    // public void updateAttendance(int newAttendance) {
    //     this.customerAttendance = newAttendance;
    // }

    // public void updateTicketsAvailable (int newTicketsAvailable) {
    //     this.ticketsAvailable = newTicketsAvailable;
    // }

}
