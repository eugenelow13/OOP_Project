package com.oop.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @ComponentScan(basePackages = {"controller", "model"})
// @EnableJpaRepositories(basePackages = "repository")
// @EntityScan(basePackages = "model")
@SpringBootApplication
@ComponentScan
public class OopApplication {

	public static void main(String[] args) {
		SpringApplication.run(OopApplication.class, args);
	}

}
