package com.bankingapplication.bankingapplication.request;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

 private String email;
 private String message;


}
