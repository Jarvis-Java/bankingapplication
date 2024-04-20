package com.bankingapplication.bankingapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingapplication.bankingapplication.entity.Account;
import com.bankingapplication.bankingapplication.request.TransactionRequest;
import com.bankingapplication.bankingapplication.services.TransactionService;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

 @Autowired
 private TransactionService transactionService;


 @GetMapping("/getTransactions/{accountId}")
 public ResponseEntity<List<TransactionRequest>> getTransactions(@RequestParam String accountId) {
     return new ResponseEntity<>(transactionService.getTransaction(accountId), HttpStatus.OK);
 }
 
 @PostMapping("/fundTransfer/{fromAccount}/{toAccount}/{amount}")
 public String fundTransfer(@RequestParam Account fromAccount, @RequestParam Account toAccount, double amount) {
     return transactionService.fundTransfer(fromAccount, toAccount, amount);
 }
 
}
