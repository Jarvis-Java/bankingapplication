package com.bankingapplication.bankingapplication.services;

import java.util.List;

import com.bankingapplication.bankingapplication.entity.Account;
import com.bankingapplication.bankingapplication.request.TransactionRequest;

public interface TransactionService {
    
    List<TransactionRequest> getTransaction(String accountId);
    
    String fundTransfer(Account fromAccount, Account toAccount, double amount);
}
