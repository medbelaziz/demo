package com.example.fraud;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FraudCheckHistory {

	@Id
	@SequenceGenerator(name = "fraud_is_sequence", sequenceName = "fraud_is_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fraud_is_sequence")
	private Integer id;

	private Integer customerId;
	private Boolean fraudster;
	private LocalDate createdAt;

}
