// package com.oop.api.service;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.List;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.context.annotation.Bean;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// import com.oop.api.model.EventStatistics;
// import com.oop.api.repository.BookingRepository;
// import com.oop.api.repository.EventRepository;
// import com.oop.api.repository.TicketRepository;

// @SpringBootTest
// @EnableJpaRepositories
// public class ReportStatisticsServiceTest {

//     @TestConfiguration
//     public static class TestEmployeeServiceConfig {    
//         @Bean
//         public ReportStatisticsService reportStatisticsService() {
//            return new ReportStatisticsService();
//         }
//     }

//     @Autowired
//     EventRepository eventRepository;

//     @Autowired
//     BookingRepository bookingRepository;

//     @Autowired
//     ReportStatisticsService reportStatisticsService;

//     @Test
//     @DisplayName("Test passes if we can successfully mark ticket as admitted if found or throw if not found")
//     public void testGetEventStatistics() {
//         List<EventStatistics> list = reportStatisticsService.getEventStatistics();
//         System.out.println(list);
        
//         assertTrue(list.size() > 0);
//     }
// }
