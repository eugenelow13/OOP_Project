package com.oop.api.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.oop.api.dto.BookingCreationDTO;
import com.oop.api.model.Booking;
import com.oop.api.model.Customer;
import com.oop.api.model.Event;
import com.oop.api.repository.BookingRepository;
import com.oop.api.repository.CustomerRepository;
import com.oop.api.repository.EventRepository;

import jakarta.persistence.EntityNotFoundException;

public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    public void placeBooking(BookingCreationDTO dto) {
        Booking booking = new Booking();

        Event event = eventRepository.findById(dto.getEventId())
            .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        Customer customer = customerRepository.findById(dto.getCustomerId())
            .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
                
        booking.setEvent(event);
        booking.setCustomer(customer);

        booking.setTickets(dto.getTickets());

        bookingRepository.save(booking);
    }
}