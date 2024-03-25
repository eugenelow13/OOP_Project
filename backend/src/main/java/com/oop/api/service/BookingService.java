package com.oop.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.api.dto.BookingCreationDTO;
import com.oop.api.dto.BookingInfo;
import com.oop.api.dto.TicketInfo;
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

    public List<BookingInfo> getAllBookings() {
        List<BookingInfo> bookingInfos = new ArrayList<>();
        Iterable<Booking> bookings = bookingRepository.findAll();
        for (Booking booking : bookings) {
            BookingInfo bookingInfo = convertToBookingInfo(booking);
            bookingInfos.add(bookingInfo);
        }
        return bookingInfos;
    }

    private BookingInfo convertToBookingInfo(Booking booking) {
        BookingInfo bookingInfo = new BookingInfo();
        bookingInfo.setId(booking.getId());
        bookingInfo.setCustomerName(booking.getCustomer().getFullName());
        bookingInfo.setCustomerEmail(booking.getCustomer().getEmail());
        bookingInfo.setEvent(booking.getEvent()); 
    
        List<TicketInfo> ticketInfos = new ArrayList<>();
        for (Ticket ticket : booking.getTickets()) {
            TicketInfo ticketInfo = new TicketInfo();
            ticketInfo.setId(ticket.getId());
            ticketInfo.setNoOfGuests(ticket.getNoOfGuests());
            ticketInfo.setIsAdmitted(ticket.getIsAdmitted());
            ticketInfos.add(ticketInfo);
        }
        bookingInfo.setTickets(ticketInfos);
    
        return bookingInfo;
    }

    public List<BookingInfo> getAllBookingsByCustomerEmail(String customerEmail) {
        List<BookingInfo> bookingInfos = new ArrayList<>();
        
        // Find the customer by email
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    
        // Retrieve all bookings associated with the customer
        List<Booking> bookings = bookingRepository.findByCustomer(customer);
    
        // Convert each booking to BookingInfo
        for (Booking booking : bookings) {
            BookingInfo bookingInfo = convertToBookingInfo(booking);
            bookingInfos.add(bookingInfo);
        }
    
        return bookingInfos;
    }


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
        
        // Set each ticket's booking, add tickets to booking
        for (Ticket ticket : requestedTickets) {
            ticket.setBooking(booking);
            booking.addTicket(ticket);
        }
        
        booking.setEvent(event);
        booking.setNoOfTickets(requestedTickets.size());
        booking.setCustomer(customer);
        
        // Update ticketsAvailable for the event
        int newTicketsAvailable = event.getTicketsAvailable() - requestedTickets.size();
        event.setTicketsAvailable(newTicketsAvailable);
        
        // Commit all changes
        customerRepository.save(customer);
        eventRepository.save(event);
        bookingRepository.save(booking);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        
        BookingInfo bookingInfo = modelMapper.map(booking, BookingInfo.class);
        System.out.println(bookingInfo.toString());

        return bookingInfo;
    }
}