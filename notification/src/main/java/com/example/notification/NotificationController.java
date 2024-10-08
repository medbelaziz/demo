package com.example.notification;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
public class NotificationController {

	private NotificationService notificationService;

	@PostMapping
	public void sendNotification(@RequestBody NotificationRequest notificationRequest) {
		log.info("New Notification ... {}", notificationRequest);
		notificationService.send(notificationRequest);
	}

}
