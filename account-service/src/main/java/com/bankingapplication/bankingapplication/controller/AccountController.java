package com.bankingapplication.bankingapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingapplication.bankingapplication.dto.AccountDto;
import com.bankingapplication.bankingapplication.services.AccountServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	@Autowired
    private AccountServiceImpl accountService;

   
    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }
    
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Double> getAccountBalance(@PathVariable String accountId) {
        return accountService.getAccountBalance(accountId);
    }

    @PostMapping("/{accountId}/deposit/amount")
    public ResponseEntity<String> deposit(@PathVariable String accountId, double amount) {
        return accountService.deposit(accountId, amount);
    }

    @PostMapping("/{accountId}/withdraw/amount")
    public ResponseEntity<String> withdraw(@PathVariable String accountId, double amount) {
        return accountService.withdraw(accountId, amount);
    }
}