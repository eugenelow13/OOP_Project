// package com.oop.api.service;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.context.annotation.Bean;
// import org.springframework.mail.MailSender;
// import org.springframework.test.context.TestPropertySource;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import com.oop.api.email.EmailService;

// @ExtendWith(SpringExtension.class)
// @SpringBootTest(properties = {"spring.mail.host=smtp.gmail.com",
//     "spring.mail.port=587",
//     "spring.mail.username=oopticketmaster@gmail.com",
//     "spring.mail.password=expi jcmu rlcs lqpo",
//     "spring.mail.properties.mail.smtp.auth=true",
//     "spring.mail.properties.mail.smtp.starttls.enable=true"})
// public class EmailServiceTest {
    
//     @TestConfiguration
//     static class EmailServiceConfig {
        
//         @Bean
//         public EmailService emailService() {
//             return new EmailService();
//         }

//     }

//     @Autowired
//     MailSender mailSender;

//     @Autowired
//     EmailService emailService;

//     @Test
//     @DisplayName("Test passes if email is sent successfully")
//     public void testSendEmail() {
//         emailService.sendEmail("seanlim2@gmail.com", "oopticketmaster@gmail.com", "HELLO TEST");
//     }
// }
