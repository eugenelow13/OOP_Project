
package com.oop.api.service;
import static com.oop.api.util.ResponseHandler.generateResponse;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oop.api.model.Booking;
import com.oop.api.model.Customer;
import com.oop.api.model.Event;
import com.oop.api.repository.BookingRepository;
import com.oop.api.repository.CustomerRepository;
import com.oop.api.repository.EventRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Iterable<Event> getAllEvents(){
        return eventRepository.findAll();
    }
    
    public Optional<Event> getEventByName(String event_name){
        return eventRepository.findByName(event_name);
    }

    public Optional<Event> getEventById(Integer id){
        return eventRepository.findById(id);
    }

    public void addNewEvent(Event event){
        eventRepository.save(event);
    }

    public Event updateEvent(Event updatedEvent) {
        // Check if the event with the given ID exists
        eventRepository.findById(updatedEvent.getId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        // Save the updated event
        return eventRepository.save(updatedEvent);
    }

    public Event cancelEvent(Integer eventId) {
        // Retrieve the event by its ID
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
    
        // Update the eventStatus to "cancelled"
        event.setEventStatus("cancelled");
    
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

    // public Event setCancellationFee(Integer eventId, double newCancellationFee) {
    //     Optional<Event> optionalEvent = eventRepository.findById(eventId);

    //     if (optionalEvent.isPresent()) {
    //         Event event = optionalEvent.get();
    //         event.setCancellationFee(newCancellationFee);
    //         return eventRepository.save(event);
    //     } else {
    //         throw new EntityNotFoundException("Event not found");
    //     }
    // }

    // public Event updateAttendence(Integer eventId, Integer newAttendance) {
    //     Optional<Event> optionalEvent = eventRepository.findById(eventId);

    //     if (optionalEvent.isPresent()) {
    //         Event event = optionalEvent.get();
    //         event.updateAttendance(newAttendance);
    //         return eventRepository.save(event);
    //     } else {
    //         throw new EntityNotFoundException("Event not found");
    //     }
    // }

    // public Event updateTicketsAvailable(Integer eventId, Integer newTicketsAvailable) {
    //     Optional<Event> optionalEvent = eventRepository.findById(eventId);

    //     if (optionalEvent.isPresent()) {
    //         Event event = optionalEvent.get();
    //         event.updateAttendance(newTicketsAvailable);
    //         return eventRepository.save(event);
    //     } else {
    //         throw new EntityNotFoundException("Event not found");
    //     }
    // }


}
