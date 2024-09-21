package com.example.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.customer.entity.Customer;
import com.example.customer.model.FraudCheckResponse;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CustomerService {
	
	@Autowired
	private EurekaClient eurekaClient;

	@Value("${fraud.resuorce.real.uri}")
	private String uriFraud;

	@Value("${fraud.resuorce.discovery.uri}")
	private String uriFraudDiscovery;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void addNewCustomer(Customer customer) {
		/*
		 * check if email not token
		 */
		Optional<Customer> customerOptional = getCustomerByEmail(customer.getEmail());
		if (customerOptional.isPresent()) {
			throw new IllegalStateException("Customer email already existe !");
		}

		/*
		 * Save Customer & flush(to get generated id )
		 */
		Customer flushedcustomer = customerRepository.saveAndFlush(customer);

		/*
		 * check if customer is fraudulent. FRAUD is the name app
		 */
		InstanceInfo obj = eurekaClient.getNextServerFromEureka("fraud", false);
		obj.getHomePageUrl();
		log.info("######### uriFraud {}", uriFraud);
		log.info("######### uriFraudDiscovery {}", uriFraudDiscovery);
		log.info("######### getHomePageUrl {}", obj.getHomePageUrl());
		FraudCheckResponse response = restTemplate.getForObject(uriFraudDiscovery + "{idCustomer}", FraudCheckResponse.class,
				flushedcustomer.getId());
		if (response != null && response.isFraudster()) {
			throw new IllegalStateException("Customer is a Fraudster!");
		}

		/*
		 * Sent notification
		 */
		String topic = "topic.customer";// class KafkaTopicConfig
		String data = "New Customer : " + flushedcustomer.toString();
		kafkaTemplate.send(topic, data);

	}

	public List<Customer> getCustomersSort() {
		return customerRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
	}

	public List<Customer> getCustomersSorted() {
		return customerRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	public List<Customer> getCustomersWithPaginatList() {
		int pageNumber = 0;
		int pageSize = 10;
		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name"));

		return customerRepository.findAll(p).getContent();
	}

	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	public Optional<Customer> getCustomerById(Long id) {
		return customerRepository.findById(id);
	}

	public boolean exisitCustomerById(Long id) {
		return customerRepository.existsById(id);
	}

	public Optional<Customer> getCustomerByEmail(String email) {
		return customerRepository.findCustomerByEmail(email);
	}

	public Optional<Customer> getCustomerByName(String name) {
		return customerRepository.findCustomerByName(name);
	}

	public void deleteCustomer(Long id) {
		Optional<Customer> customerOptional = getCustomerById(id);
		if (customerOptional.isEmpty()) {
			throw new IllegalStateException("Customer ID : " + id + " doesn't existe !");
		}
		customerRepository.delete(customerOptional.get());
	}

	@Transactional
	public void updateCustomer(Customer customerToUpdateed) {

		if (!exisitCustomerById(customerToUpdateed.getId())) {
			throw new IllegalStateException("Customer ID : " + customerToUpdateed.getId() + " doesn't existe !");
		}

		if (isValidEmail(customerToUpdateed) && isValidName(customerToUpdateed)) {

			if (customerRepository.findCustomerByEmail(customerToUpdateed.getEmail()).isPresent()) {
				throw new IllegalStateException("Customer already existe !");
			}
			customerRepository.save(customerToUpdateed);
		}
	}

	private boolean isValidEmail(Customer customerToUpdateed) {
		return customerToUpdateed.getEmail() != null && customerToUpdateed.getEmail().contains("@");
	}

	private boolean isValidName(Customer customerToUpdateed) {
		return customerToUpdateed.getName() != null && customerToUpdateed.getName().length() > 0;
	}
}
