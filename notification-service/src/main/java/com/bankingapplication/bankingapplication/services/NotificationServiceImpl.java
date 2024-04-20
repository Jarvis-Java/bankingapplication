package com.bankingapplication.bankingapplication.services;

import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	public void sendNotification(String email, String message) {
	     System.out.println("Sending email notification to: " + email);
	     System.out.println("Message: " + message);
	 }

}
