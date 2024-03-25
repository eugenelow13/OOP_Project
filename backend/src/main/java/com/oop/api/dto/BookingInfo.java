package com.oop.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.oop.api.model.Event;

@Getter @Setter
@ToString
public class BookingInfo {
    private int id;
    private String customerName;
    private String customerEmail;
    private double bookingPrice;
    private boolean isCancelled;
    private Event event;
    List<TicketInfo> tickets;
}
