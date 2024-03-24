package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oop.api.dto.BookingCreationDTO;
import com.oop.api.dto.BookingInfo;
import com.oop.api.service.BookingService;

@RestController
@RequestMapping("bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

     @GetMapping("")
    public ResponseEntity<Object> getAllBookings() {
        List<BookingInfo> bookings = bookingService.getAllBookings();
        return generateResponse("All bookings retrieved successfully", bookings, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Object> placeBooking(@RequestBody BookingCreationDTO dto) {
        BookingInfo booking = bookingService.placeBooking(dto);
        return generateResponse("Booking placed successfully", booking, HttpStatus.CREATED);
    }
}
