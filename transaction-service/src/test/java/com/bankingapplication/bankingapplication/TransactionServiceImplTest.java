package com.bankingapplication.bankingapplication;

import com.bankingapplication.bankingapplication.entity.Account;
import com.bankingapplication.bankingapplication.repositories.TransactionRepository;
import com.bankingapplication.bankingapplication.request.NotificationRequest;
import com.bankingapplication.bankingapplication.request.TransactionRequest;
import com.bankingapplication.bankingapplication.services.TransactionServiceImpl;
import com.bankingapplication.bankingapplication.feignClient.NotificationClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private NotificationClient notificationService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTransaction() {
        String accountId = "123456";
        List<com.bankingapplication.bankingapplication.entity.Transaction> transactions = new ArrayList<>();
        com.bankingapplication.bankingapplication.entity.Transaction transaction = new com.bankingapplication.bankingapplication.entity.Transaction();
        transaction.setId(1L);
        transaction.setId(Long.valueOf(accountId));
        transactions.add(transaction);

        when(transactionRepository.findById(Long.valueOf(accountId)));

        List<TransactionRequest> result = transactionService.getTransaction(accountId);

        assertEquals(1, result.size());
        assertEquals(transaction.getId(), result.get(0));
        assertEquals(transaction.getAccountId(), Long.parseLong(result.get(0).getAccountId()));
    }

    @Test
    public void testFundTransfer() {
        Account fromAccount = new Account();
        fromAccount.setBalance(1000.00);
        Account toAccount = new Account();
        toAccount.setBalance(500.00);
        double amount = 200.00;

        when(notificationService.sendNotification(any(NotificationRequest.class)));

        String result = transactionService.fundTransfer(fromAccount, toAccount, amount);

        assertEquals(UUID.class, result.getClass());
        assertEquals(800.00, fromAccount.getBalance());
        assertEquals(700.00, toAccount.getBalance());
    }
}
