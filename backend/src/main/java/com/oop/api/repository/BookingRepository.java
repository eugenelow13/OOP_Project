package com.oop.api.repository;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oop.api.model.Booking;
import com.oop.api.model.Customer;
/**
 * The BookingRepository interface provides methods to interact with the database for booking-related operations.
 */
@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

    /**
     * Retrieves a list of bookings associated with a specific event ID.
     *
     * @param eventId The ID of the event.
     * @return A list of bookings associated with the specified event ID.
     */
    @Query("SELECT b FROM Booking b WHERE b.event.id = ?1")
    List<Booking> findByEventId(int eventId);

    /**
     * Retrieves a list of bookings associated with a specific customer.
     *
     * @param customer The customer object.
     * @return A list of bookings associated with the specified customer.
     */
    List<Booking> findByCustomer(Customer customer);

}
