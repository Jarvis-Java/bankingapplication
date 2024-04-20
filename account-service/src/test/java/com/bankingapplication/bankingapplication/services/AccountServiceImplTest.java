package com.bankingapplication.bankingapplication.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bankingapplication.bankingapplication.dto.AccountDto;
import com.bankingapplication.bankingapplication.entity.Account;
import com.bankingapplication.bankingapplication.feignClient.NotificationClient;
import com.bankingapplication.bankingapplication.repositories.AccountRepository;
import com.bankingapplication.bankingapplication.request.NotificationRequest;

public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private NotificationClient notificationService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAccount() {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(123456L);
        accountDto.setBalance(1000.00);

        Account account = new Account();
        account.setEmail("test@example.com");
        
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEmail("test@example.com");
        notificationRequest.setMessage("Test Message from junit");

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        ResponseEntity<String> response = accountService.createAccount(accountDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account Service : Account created successfully.", response.getBody());

        verify(notificationService, times(1)).sendNotification(notificationRequest);
    }

    @Test
    public void testGetAccountBalance() {
        String accountNumber = "123456";
        double balance = 1500.00;

        Account account = new Account();
        account.setBalance(balance);

        when(accountRepository.findById(Long.valueOf(accountNumber))).thenReturn(Optional.of(account));

        ResponseEntity<Double> response = accountService.getAccountBalance(accountNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(balance, response.getBody());
    }

    @Test
    public void testDeposit() {
        String accountNumber = "123456";
        double amount = 500.00;
        double initialBalance = 1000.00;
        double newBalance = initialBalance + amount;

        Account account = new Account();
        account.setBalance(initialBalance);
        account.setEmail("test@example.com");

        when(accountRepository.findById(Long.valueOf(accountNumber))).thenReturn(Optional.of(account));

        ResponseEntity<String> response = accountService.deposit(accountNumber, amount);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account Service : Deposit successful. New balance: $" + newBalance, response.getBody());

        verify(notificationService, times(1)).sendNotification(any(NotificationRequest.class));
    }

    @Test
    public void testWithdrawSufficientBalance() {
        String accountNumber = "123456";
        double amount = 200.00;
        double initialBalance = 500.00;
        double newBalance = initialBalance - amount;

        Account account = new Account();
        account.setBalance(initialBalance);
        account.setEmail("test@example.com");

        when(accountRepository.findById(Long.valueOf(accountNumber))).thenReturn(Optional.of(account));

        ResponseEntity<String> response = accountService.withdraw(accountNumber, amount);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Withdrawal successful. New balance: $" + newBalance, response.getBody());

        verify(notificationService, times(1)).sendNotification(any(NotificationRequest.class));
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        String accountNumber = "123456";
        double amount = 800.00;
        double initialBalance = 500.00;

        Account account = new Account();
        account.setBalance(initialBalance);

        when(accountRepository.findById(Long.valueOf(accountNumber))).thenReturn(Optional.of(account));

        ResponseEntity<String> response = accountService.withdraw(accountNumber, amount);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Insufficient balance.", response.getBody());

        verify(notificationService, never()).sendNotification(any(NotificationRequest.class));
    }

    @Test
    public void testWithdrawAccountNotFound() {
        String accountNumber = "123456";
        double amount = 200.00;

        when(accountRepository.findById(Long.valueOf(accountNumber))).thenReturn(Optional.empty());

        ResponseEntity<String> response = accountService.withdraw(accountNumber, amount);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(notificationService, never()).sendNotification(any(NotificationRequest.class));
    }
}
