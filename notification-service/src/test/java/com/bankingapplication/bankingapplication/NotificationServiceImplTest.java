package com.bankingapplication.bankingapplication;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bankingapplication.bankingapplication.services.NotificationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {


    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    public void testSendNotification() {
    	
          String message = "Test message";
          String email = "\"test@example.com\"";
        notificationService.sendNotification(email, message);

    }
}
