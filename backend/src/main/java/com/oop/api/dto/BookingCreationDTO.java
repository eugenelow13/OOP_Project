package com.oop.api.dto;

import java.util.List;
import com.oop.api.model.Ticket;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a data transfer object for creating a booking.
 */

@Getter @Setter @ToString
public class BookingCreationDTO {
    private Integer eventId;
    private Integer customerId;
    private List<Ticket> tickets;
    private String password;
}
