package com.bankingapplication.bankingapplication;

import com.bankingapplication.bankingapplication.controller.TransactionController;
import com.bankingapplication.bankingapplication.entity.Account;
import com.bankingapplication.bankingapplication.request.TransactionRequest;
import com.bankingapplication.bankingapplication.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTransactions() {
        String accountId = "123456";
        List<TransactionRequest> transactions = new ArrayList<>();

        when(transactionService.getTransaction(accountId)).thenReturn(transactions);

        ResponseEntity<List<TransactionRequest>> response = transactionController.getTransactions(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
    }

    @Test
    public void testFundTransfer() {
        Account fromAccount = new Account();
        Account toAccount = new Account();
        double amount = 500.00;
        String expectedResult = "Transaction successful";

        when(transactionService.fundTransfer(fromAccount, toAccount, amount)).thenReturn(expectedResult);

        String result = transactionController.fundTransfer(fromAccount, toAccount, amount);

        assertEquals(expectedResult, result);
    }
}
