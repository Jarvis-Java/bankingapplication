package com.bankingapplication.bankingapplication;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bankingapplication.bankingapplication.controller.NotificationController;
import com.bankingapplication.bankingapplication.request.NotificationRequest;
import com.bankingapplication.bankingapplication.services.NotificationService;

public class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendNotification() {
        NotificationRequest notificationRequest = new NotificationRequest("test@example.com", "Test message");

        ResponseEntity<String> response = notificationController.sendNotification(notificationRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notification Service : Notification sent successfully.", response.getBody());

        verify(notificationService, times(1)).sendNotification(notificationRequest.getEmail(), notificationRequest.getMessage());
    }
}
