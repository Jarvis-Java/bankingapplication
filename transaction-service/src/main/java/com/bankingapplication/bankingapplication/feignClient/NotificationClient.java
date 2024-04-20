package com.bankingapplication.bankingapplication.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bankingapplication.bankingapplication.request.NotificationRequest;


@FeignClient(name = "notification-application", url = "http://localhost:8082", path = "/api/v1/notification")
public interface NotificationClient {

	 @PostMapping
	 public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest notificationRequest);
	
}
