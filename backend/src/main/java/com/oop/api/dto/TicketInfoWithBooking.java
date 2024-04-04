package com.oop.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class TicketInfoWithBooking {
    private String bookingCustomerName;
    private String bookingCustomerEmail;
    private Integer bookingId;
    private Integer id;
    private Integer noOfGuests;
    private Boolean isAdmitted;
}
