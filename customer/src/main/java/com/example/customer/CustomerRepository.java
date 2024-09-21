package com.example.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.customer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findCustomerByEmail(String email);

	Optional<Customer> findCustomerByName(String name);

	// query => without naming convention
	@Query("SELECT c FROM Customer c")
	List<Customer> findAllCustomers(Sort sort);

	// query => without naming convention
	@Query("SELECT c FROM Customer c")
	Page<Customer> findAllCustomersWithPaginatList(Pageable p);

	// a mettre au dessus de la class entity
	// @NamedQuery(name = "",query="")
	// @NamedNativeQuery(name = "",query="")
}
