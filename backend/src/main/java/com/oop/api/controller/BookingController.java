package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oop.api.dto.BookingCreationDTO;
import com.oop.api.dto.BookingInfo;
import com.oop.api.service.BookingService;

@RestController
@RequestMapping("bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("")
    public ResponseEntity<Object> placeBooking(@RequestBody BookingCreationDTO dto) {
        BookingInfo booking = bookingService.placeBooking(dto);
        return generateResponse("Booking placed successfully", booking, HttpStatus.CREATED);
    }
}