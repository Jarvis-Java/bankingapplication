package com.bankingapplication.bankingapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingapplication.bankingapplication.request.NotificationRequest;
import com.bankingapplication.bankingapplication.services.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

 @Autowired
 private NotificationService notificationService;

 @PostMapping
 public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest notificationRequest) {

     notificationService.sendNotification(notificationRequest.getEmail(), notificationRequest.getMessage());

     return ResponseEntity.ok("Notification Service : Notification sent successfully.");
 }
}
