package com.oop.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents information about a ticket.
 */

@Getter @Setter
@ToString
public class TicketInfo {
    
    /**
     * The ID of the ticket.
     */

    private Integer id;
    
    /**
     * The number of guests associated with the ticket.
     */

    private Integer noOfGuests;
    
    /**
     * Indicates whether the ticket has been admitted or not.
     */

    private Boolean isAdmitted;
}
