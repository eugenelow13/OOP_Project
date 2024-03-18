// package com.oop.api.service;

// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.Mockito.when;

// import java.util.Optional;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import com.oop.api.exception.TicketNotFoundException;
// import com.oop.api.model.Ticket;
// import com.oop.api.repository.TicketRepository;

// @ExtendWith(SpringExtension.class)
// public class BookingServiceTest {

//     @TestConfiguration
//     static class BookingServiceConfig {

//         @Bean
//         public BookingService bookingService() {
//             return new BookingService();
//         }
//     }

//     @Autowired
//     BookingService bookingService;

//     @MockBean
//     private TicketRepository ticketRepository;

//     @MockBean
//     private CustomerRepository customerRepository;
    
//     @MockBean
//     private TicketRepository ticketRepository;

//     @Test
//     @DisplayName("Test passes if we can successfully mark ticket as admitted if found or throw if not found")
//     public void testMarkTicketAsAdmitted() {
//         Ticket t = new Ticket();
//         t.setId(12345);

//         when(ticketRepository.findById(0)).thenReturn(Optional.empty());
//         when(ticketRepository.findById(12345)).thenReturn(Optional.of(t));

//         // If ticket not found
//         assertThrows(TicketNotFoundException.class, () -> {
//             ticketService.markTicketAsAdmitted(0);
//         });
        
//         // when ticket is found
//         ticketService.markTicketAsAdmitted(12345);

//         assertTrue(t.getIsAdmitted());
//     }
// }
