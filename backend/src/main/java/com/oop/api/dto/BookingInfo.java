package com.oop.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class BookingInfo {
    private int id;
    private String customerName;
    List<TicketInfo> tickets;
}
