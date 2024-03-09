package com.oop.api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    private Customer customer;

    private int noOfTickets;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<Ticket> tickets = new ArrayList<>();

    // @OneToOne
    // private PaymentDetails paymentDetails;

    private boolean isCancelled;

    public void addTicket (Ticket newTicket) {
        this.tickets.add(newTicket);
    }
}
