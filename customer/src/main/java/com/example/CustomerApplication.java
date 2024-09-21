package com.example;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.customer.CustomerRepository;
import com.example.customer.entity.Customer;

@SpringBootApplication
@EnableJpaRepositories()
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository repository) {
		return args -> repository.save(new Customer("newCustomer", "customer@gmail.com", LocalDate.now()));
	}
}
