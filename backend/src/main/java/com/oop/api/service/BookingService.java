package com.oop.api.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.api.dto.BookingCreationDTO;
import com.oop.api.dto.BookingInfo;
import com.oop.api.model.Booking;
import com.oop.api.model.Customer;
import com.oop.api.model.Event;
import com.oop.api.model.Ticket;
import com.oop.api.repository.BookingRepository;
import com.oop.api.repository.CustomerRepository;
import com.oop.api.repository.EventRepository;
import com.oop.api.repository.TicketRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private TicketRepository ticketRepository;

    public BookingInfo placeBooking(BookingCreationDTO dto) {
        Booking booking = new Booking();
        
        Event event = eventRepository.findById(dto.getEventId())
        .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        
        Customer customer = customerRepository.findById(dto.getCustomerId())
        .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // Add tickets to booking
        List<Ticket> requestedTickets = dto.getTickets();


        // Get total price 
        double totalAmount = 0.0;
        totalAmount += event.getTicketPrice() * requestedTickets.size();


        if (customer.getCreditBalance() < totalAmount) {
            throw new IllegalArgumentException("Insufficient credit balance to make the booking");
        }

        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Customer Balance: " + customer.getCreditBalance());

        // Deduct the total amount from the customer's credit balance
        customer.setCreditBalance(customer.getCreditBalance() - totalAmount);
        customerRepository.save(customer);

        


        for (Ticket ticket : requestedTickets) {
            ticket.setBooking(booking);
            booking.addTicket(ticket);
        }

        booking.setEvent(event);
        booking.setCustomer(customer);


        // Update ticketsAvailable for the event
        int newTicketsAvailable = event.getTicketsAvailable() - requestedTickets.size();
        event.setTicketsAvailable(newTicketsAvailable);
        eventRepository.save(event);

        // event.addBooking(booking);
        // eventRepository.save(event);

        bookingRepository.save(booking);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        
        BookingInfo bookingInfo = modelMapper.map(booking, BookingInfo.class);
        System.out.println(bookingInfo.toString());

        return bookingInfo;
    }
}