package com.example.notification.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaListeners {

	@KafkaListener(topics = "topic.customer", groupId = "groupId")
	void listener(String data) {
		log.info("############################");
		log.info("Listner received : " + data);
		log.info("############################");
	}
}
