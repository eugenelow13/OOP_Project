package com.oop.api.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.api.model.Booking;
import com.oop.api.model.Customer;
import com.oop.api.model.Event;
import com.oop.api.model.EventStatus;
import com.oop.api.repository.BookingRepository;
import com.oop.api.repository.CustomerRepository;
import com.oop.api.repository.EventRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * The EventService class provides methods to interact with events in the system.
 * It handles operations such as retrieving events, adding new events, updating events, and canceling events.
 */
@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Retrieves all events from the event repository.
     *
     * @return an Iterable of Event objects representing all events.
     */
    public Iterable<Event> getAllEvents(){
        return eventRepository.findAll();
    }
    
    /**
     * Retrieves an event by its name.
     *
     * @param event_name the name of the event to retrieve
     * @return an Optional containing the event if found, or an empty Optional if not found
     */
    public Optional<Event> getEventByName(String event_name){
        return eventRepository.findByName(event_name);
    }

    /**
     * Retrieves an event by its ID.
     *
     * @param id the ID of the event to retrieve
     * @return an Optional containing the event if found, or an empty Optional if not found
     */
    public Optional<Event> getEventById(Integer id){
        return eventRepository.findById(id);
    }

    /**
     * Adds a new event to the system.
     *
     * @param event the event to be added
     */
    public void addNewEvent(Event event){
        eventRepository.save(event);
    }

    /**
     * Updates an existing event in the system.
     * @param updatedEvent The updated event object
     * @return The updated event object
     */
    public Event updateEvent(Event updatedEvent) {
        // Check if the event with the given ID exists
        eventRepository.findById(updatedEvent.getId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        // Save the updated event
        return eventRepository.save(updatedEvent);
    }

    /**
        * Cancels an event by updating its status to CANCELLED and performing additional computations.
        * 
        * @param eventId the ID of the event to be cancelled
        * @return the updated event
        * @throws EntityNotFoundException if the event with the specified ID is not found
        */
    public Event cancelEvent(Integer eventId) {
        // Retrieve the event by its ID
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
    
        // Update the eventStatus to CANCELLED
        event.setEventStatus(EventStatus.CANCELLED);
    
        // Perform additional computations (commented out for now)
        // You can uncomment and add your computations here
        // Get all bookings with eventId of the cancelled event
        List<Booking> bookings = bookingRepository.findByEventId(eventId);
        
        // Set isCancelled to true for each booking and deposit booking fee to customer's credit balance
        for (Booking booking : bookings) {
            // Set isCancelled to true
            System.out.println("Bookings for the event are: " + booking.getId());

            booking.setCancelled(true);

            // Deposit booking fee to customer's credit balance
            Customer customer = booking.getCustomer();
            System.out.println("Customer: " + customer.getFullName());
            double bookingFee = booking.getNoOfTickets() * booking.getEvent().getTicketPrice();
            System.out.println("No of Tickets: " + booking.getNoOfTickets());
            System.out.println("Event Ticket Price: " + booking.getEvent().getTicketPrice());
            System.out.println("Booking Fee: " + bookingFee);
            customer.setCreditBalance(customer.getCreditBalance() + bookingFee);

            // Save the updated booking and customer
            bookingRepository.save(booking);
            customerRepository.save(customer);
        }
        // Save the updated event
        return updateEvent(event);
    }
}
