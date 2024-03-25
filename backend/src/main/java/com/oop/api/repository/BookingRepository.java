package com.oop.api.repository;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oop.api.model.Booking;
import com.oop.api.model.Customer;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {
    @Query("SELECT b FROM Booking b WHERE b.event.id = ?1")
    List<Booking> findByEventId(int eventId);

    List<Booking> findByCustomer(Customer customer);

}
