package com.example.fraud;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class FraudCheckHistoryService {

	private FraudCheckHistoryRepository fraudCheckHistoryRepository;

	public FraudCheckResponse isFraudulenCustomer(Integer idCustomer) {
		fraudCheckHistoryRepository.save(
				FraudCheckHistory.builder()
				.customerId(idCustomer)
				.createdAt(LocalDate.now())
				.fraudster(false)
				.build());
		log.info("Checkin fraudlster for customer {}", idCustomer);
		return new FraudCheckResponse(false);
	}

}
