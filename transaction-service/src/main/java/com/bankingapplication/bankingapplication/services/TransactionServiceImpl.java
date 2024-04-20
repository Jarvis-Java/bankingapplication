package com.bankingapplication.bankingapplication.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingapplication.bankingapplication.entity.Account;
import com.bankingapplication.bankingapplication.feignClient.NotificationClient;
import com.bankingapplication.bankingapplication.repositories.TransactionRepository;
import com.bankingapplication.bankingapplication.request.NotificationRequest;
import com.bankingapplication.bankingapplication.request.TransactionRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	public TransactionRepository transactionRepository;
	
	 @Autowired
	 private final NotificationClient notificationService;

	@Override
	public List<TransactionRequest> getTransaction(String accountId) {
		
		 return transactionRepository.findById(Long.valueOf(accountId)).stream().map(transaction -> {
	                    TransactionRequest transactionRequest = new TransactionRequest();
	                    BeanUtils.copyProperties(transaction, transactionRequest);
	                    return transactionRequest;
	                }).collect(Collectors.toList());
	}

	@Override
	public String fundTransfer(Account fromAccount, Account toAccount, double amount) {
		
		fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        String message = "Your Account has been debited "+String.valueOf(amount);
        NotificationRequest notification = new NotificationRequest(toAccount.getEmail(),message);

        // Send notification
       notificationService.sendNotification(notification);


        return UUID.randomUUID().toString();
	}

	

}
