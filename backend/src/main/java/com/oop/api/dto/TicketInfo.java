package com.oop.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class TicketInfo {
    private Integer id;
    private Integer noOfGuests;
    private Boolean isAdmitted;
}
