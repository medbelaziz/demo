package com.example.fraud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/fraud-check")
@AllArgsConstructor
public class FraudCheckHistoryController {
	
	private FraudCheckHistoryService fraudCheckHistoryService;

	@GetMapping("/{id}")
	public FraudCheckResponse isFraudster(@PathVariable("id") Integer idCustomer) {
		return fraudCheckHistoryService.isFraudulenCustomer(idCustomer);
	}

}
