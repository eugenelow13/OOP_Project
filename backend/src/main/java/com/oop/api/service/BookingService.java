package com.oop.api.service;

import java.time.LocalDateTime;
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
        bookingInfo.setBookingPrice(booking.getBookingPrice());
        // bookingInfo.setIsCancelled(false);
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

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventStart = event.getDate();

        // Check if booking is within 6 months in advance
        LocalDateTime sixMonthsInAdvance = now.plusMonths(6);
        if (eventStart.isAfter(sixMonthsInAdvance)) {
            throw new IllegalArgumentException("Booking cannot be made more than 6 months in advance");
        }

        // Check if booking is at least 24 hours before the event start
        LocalDateTime twentyFourHoursBeforeEvent = eventStart.minusHours(24);
        if (now.isAfter(twentyFourHoursBeforeEvent)) {
            throw new IllegalArgumentException("Booking cannot be made less than 24 hours before the event start");
        }



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
        booking.setBookingPrice(totalAmount);
        booking.setCancelled(false);
        
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

    public BookingInfo cancelBooking(int bookingId, String customerEmail) {
        
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
    
        
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    
        // Check if the booking belongs to the customer so that customer cant just delete any booking ID
        if (!booking.getCustomer().equals(customer)) {
            throw new IllegalArgumentException("Booking does not belong to the customer");
        }
    
        // Check if the booking is within 48 hours before the event start
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventStart = booking.getEvent().getDate();
        LocalDateTime fortyEightHoursBeforeEvent = eventStart.minusHours(48);
        if (now.isAfter(fortyEightHoursBeforeEvent)) {
            throw new IllegalArgumentException("Booking cannot be canceled less than 48 hours before the event start");
        }
    
        // Refund the booking price to the customer's credit balance
        // Refund will be the booking amount - the cancellation fee
        double bookingPrice = booking.getBookingPrice();
        double cancellationFee = booking.getEvent().getCancellationFee();
        double refundAmount = bookingPrice - cancellationFee;
        customer.setCreditBalance(customer.getCreditBalance() + refundAmount);
    
        // Update ticketsAvailable for the event
        int newTicketsAvailable = booking.getEvent().getTicketsAvailable() + booking.getNoOfTickets();
        booking.getEvent().setTicketsAvailable(newTicketsAvailable);
    
       
        booking.setCancelled(true);
    
        
        customerRepository.save(customer);
        eventRepository.save(booking.getEvent());
        bookingRepository.save(booking);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        
        BookingInfo bookingInfo = modelMapper.map(booking, BookingInfo.class);
        System.out.println(bookingInfo.toString());

        return bookingInfo;
    }
    

}