package com.oop.api.email;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {
    // @Bean
	// public JavaMailSender getJavaMailSender()
	// {
	//     JavaMailSender mailSender = new JavaMailSenderImpl();
	//     return mailSender;
	// }
}
