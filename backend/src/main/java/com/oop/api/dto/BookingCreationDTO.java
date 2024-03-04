package com.oop.api.dto;

import java.util.ArrayList;

import com.oop.api.model.Ticket;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookingCreationDTO {
    private Integer eventId;
    private Integer customerId;
    private ArrayList<Ticket> tickets;
}
