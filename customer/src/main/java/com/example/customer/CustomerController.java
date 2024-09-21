package com.example.customer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.entity.Customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping
	public List<Customer> getCustomers(Authentication authentication) {
		log.info("getCustomers() :  {}" + Map.of(
				"USERNAME",authentication.getName(),
				"SCOPE",authentication.getAuthorities().stream().map(autho -> autho.getAuthority()).collect(Collectors.joining(" "))
				));
		log.info("getCustomers() :  {}" + customerService.getCustomers());
		return customerService.getCustomers();
	}

	@PostMapping
	public void registerNewCustomer(@RequestBody Customer customer) {
		log.info("registerNewCustomer() :  {}" + customer);
		customerService.addNewCustomer(customer);
	}

	@PutMapping()
	public void updateCustomer(@RequestBody Customer customer) {
		log.info("updateCustomer() :  {}" + customer);
		customerService.updateCustomer(customer);
	}

	@DeleteMapping("{id}")
	public void deleteCustomer(@PathVariable Long id) {
		log.warn("updateCustomer({})id" + id);
		customerService.deleteCustomer(id);
	}

}
