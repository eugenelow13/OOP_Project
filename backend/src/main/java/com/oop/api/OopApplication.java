package com.oop.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@ComponentScan
public class OopApplication {
	/**
	 * The entry point of the OOP application.
	 *
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(OopApplication.class, args);
	}
}
