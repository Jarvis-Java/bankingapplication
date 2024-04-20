package com.bankingapplication.bankingapplication.services;


import org.springframework.http.ResponseEntity;

import com.bankingapplication.bankingapplication.dto.AccountDto;

public interface AccountService {

    
	ResponseEntity<String> createAccount(AccountDto accountDto);
    
    ResponseEntity<Double> getAccountBalance(String accountNumber);

    ResponseEntity<String> deposit(String accountNumber, double amount);
    
    ResponseEntity<String> withdraw(String accountNumber, Double amount);
}