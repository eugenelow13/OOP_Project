package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.oop.api.dto.BookingCreationDTO;
import com.oop.api.dto.BookingInfo;
import com.oop.api.service.BookingService;
import com.oop.api.service.EmailService;

import jakarta.validation.constraints.Email;

/**
 * The BookingController class handles the HTTP requests related to bookings.
 * It provides endpoints for retrieving all bookings, retrieving bookings by customer email,
 * placing a new booking, and canceling a booking.
 */

@RestController
@RequestMapping("bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    @GetMapping("")
    public ResponseEntity<Object> getAllBookings() {
        List<BookingInfo> bookings = bookingService.getAllBookings();
        return generateResponse("All bookings retrieved successfully", bookings, HttpStatus.OK);
    }

    // Get bookings by Customer Email
    @GetMapping("/customers/bookings")
    public ResponseEntity<Object> getCustomerBookingsByEmail(@RequestParam("email") @Email String customerEmail) {
        List<BookingInfo> customerBookings = bookingService.getAllBookingsByCustomerEmail(customerEmail);
        return generateResponse("Customer bookings retrieved successfully", customerBookings, HttpStatus.OK);
    }

    @PostMapping("/placeBooking")
    public ResponseEntity<Object> placeBooking(@RequestBody BookingCreationDTO bookingCreationDTO) {
        BookingInfo booking = bookingService.placeBooking(bookingCreationDTO);
        try {
            emailService.sendEmail(booking.getCustomerEmail(), booking);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return generateResponse("Email sent successfully", null, HttpStatus.OK);
    }

    @DeleteMapping("/cancelBooking")
    public ResponseEntity<Object> cancelBooking(@RequestParam int bookingId, @RequestParam String email) {
        BookingInfo canceledBooking = bookingService.cancelBooking(bookingId, email);
        return generateResponse("Booking canceled successfully", canceledBooking, HttpStatus.OK);
    }
}
